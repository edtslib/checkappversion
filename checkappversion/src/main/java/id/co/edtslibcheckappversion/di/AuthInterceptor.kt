package id.co.edtslibcheckappversion.di

import id.co.edtslibcheckappversion.CheckAppVersion
import id.co.edtslibcheckappversion.utils.SecurityUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * A {@see RequestInterceptor} that adds an auth token to requests
 */
class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("x-api-key", "")
        if (CheckAppVersion.privateKeyFileContent != null &&
            CheckAppVersion.defaultPayload != null && CheckAppVersion.enableSignature) {

            val privateKey = SecurityUtil.getPrivateKeyFromKeyStore(CheckAppVersion.privateKeyFileContent!!.split(", "))
            builder.addHeader("signature",
                SecurityUtil.signWithPayload(CheckAppVersion.defaultPayload!!, privateKey))
        }

        return chain.proceed(builder.build())
    }
}