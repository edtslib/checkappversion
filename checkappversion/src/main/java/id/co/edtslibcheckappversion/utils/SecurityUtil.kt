package id.co.edtslibcheckappversion.utils

import android.util.Base64
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec

class SecurityUtil {

    companion object {

        @Throws(IOException::class, NoSuchAlgorithmException::class, InvalidKeySpecException::class)
        fun getPrivateKeyFromKeyStore(privateKeyList: List<String>): PrivateKey? {
            var privateKey = ""
            var inKey = false
            for (line in privateKeyList) {
                if (!inKey) {
                    if (line.startsWith("-----BEGIN ") && line.endsWith(" PRIVATE KEY-----")) {
                        inKey = true
                    }
                } else {
                    if (line.startsWith("-----END ") && line.endsWith(" PRIVATE KEY-----")) {
                        break
                    }
                    privateKey += line
                }
            }
            return try {
                val encoded = Base64.decode(privateKey, Base64.NO_WRAP)
                val keySpec = PKCS8EncodedKeySpec(encoded)
                val kf = KeyFactory.getInstance("RSA")
                kf.generatePrivate(keySpec)
            } catch (e: Exception) {
                null
            }
        }

        fun signWithPayload(payload: String, privateKey: PrivateKey?): String {
            val privateSignature = Signature.getInstance("SHA256withRSA")
            privateSignature.initSign(privateKey)
            privateSignature.update(payload.toByteArray(StandardCharsets.UTF_8))
            val signature = privateSignature.sign()
            return Base64.encodeToString(signature, Base64.NO_WRAP)
        }

    }

}