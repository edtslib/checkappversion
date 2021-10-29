package id.co.edtslibcheckappversion.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val mainAppModule = module {
    single { provideCheckAppVersionApiService(get(named("checkappversion"))) }
}

private fun provideCheckAppVersionApiService(retrofit: Retrofit): CheckAppVersionApiService =
    retrofit.create(CheckAppVersionApiService::class.java)