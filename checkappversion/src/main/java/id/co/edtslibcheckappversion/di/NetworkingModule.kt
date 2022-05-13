package id.co.edtslibcheckappversion.di

import com.google.gson.Gson
import id.co.edtslibcheckappversion.CheckAppVersion
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkingModule = module {
    single(named("checkappversionOkHttpClient")) { provideOkHttpClient() }
    single { provideGson() }
    single { provideGsonConverterFactory(get()) }

    single(named("checkappversion")) { provideRetrofit(get(named("checkappversionOkHttpClient")), get()) }
}

private fun provideOkHttpClient(): OkHttpClient = UnsafeOkHttpClient().get()

private fun provideGson(): Gson = Gson()

private fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
    GsonConverterFactory.create(gson)

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(CheckAppVersion.endPoint)
        .client(okHttpClient.newBuilder().addInterceptor(AuthInterceptor()).build())
        .addConverterFactory(converterFactory)
        .build()
}