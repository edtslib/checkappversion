package id.co.edtslibcheckappversion.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import id.co.edtslibcheckappversion.CheckAppVersion
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class SafeOkHttpClient {

    fun get(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (CheckAppVersion.debugging) HttpLoggingInterceptor.Level.BODY else
                HttpLoggingInterceptor.Level.NONE }

        return try {
            val builder = OkHttpClient.Builder()
            if (CheckAppVersion.sslPinner.isNotEmpty() && CheckAppVersion.sslDomain.isNotEmpty()) {
                val certificatePinner = CertificatePinner.Builder()
                    .add(
                        CheckAppVersion.sslDomain,
                        CheckAppVersion.sslPinner
                    )
                    .build()
                builder.certificatePinner(certificatePinner)
            }

            builder.addInterceptor(interceptor)
            builder.addNetworkInterceptor(StethoInterceptor())

            if (CheckAppVersion.trustManagerFactory != null) {
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, CheckAppVersion.trustManagerFactory!!.trustManagers, SecureRandom())

                val sslSocketFactory = sslContext.socketFactory
                builder.sslSocketFactory(sslSocketFactory, CheckAppVersion.trustManagerFactory!!.trustManagers[0] as X509TrustManager)
            }

            builder.build()
        } catch (e: Exception) {
            OkHttpClient.Builder().addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }
    }
}