package id.co.edtslibcheckappversion.di

import id.co.edtslibcheckappversion.data.IRepository
import id.co.edtslibcheckappversion.data.RemoteDataSource
import id.co.edtslibcheckappversion.data.Repository
import id.co.edtslibcheckappversion.data.VersionLocalDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { VersionLocalDataSource(get(named("appVersionSharePrefs"))) }

    single<IRepository> { Repository(get(), get()) }
}