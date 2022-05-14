package id.co.edtslibcheckappversion.di

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.securepreferences.SecurePreferences
import id.co.edtslibcheckappversion.BuildConfig
import id.co.edtslibcheckappversion.CheckAppVersion
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.lang.Exception

val sharedPreferencesModule = module {
    single(named("appVersionSharePrefs")) {
        if (CheckAppVersion.debugging) {
            PreferenceManager.getDefaultSharedPreferences(androidContext())
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val spec = KeyGenParameterSpec.Builder(
                        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                        .build()
                    val masterKey = MasterKey.Builder(get())
                        .setKeyGenParameterSpec(spec)
                        .build()

                    EncryptedSharedPreferences.create(
                        androidContext(),
                        "edts_checkappversion_secret_shared_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    )
                } else {
                    SecurePreferences(
                        androidContext(),
                        BuildConfig.DB_PASS,
                        "edts_checkappversion_secret_shared_prefs"
                    )
                }
            }
            catch (e: Exception) {

                PreferenceManager.getDefaultSharedPreferences(androidContext())
            }
            catch (e: NoClassDefFoundError) {
                PreferenceManager.getDefaultSharedPreferences(androidContext())
            }

        }
    }
}