package id.co.edtslibcheckappversion.di

import id.co.edtslibcheckappversion.data.IRepository
import id.co.edtslibcheckappversion.data.RemoteDataSource
import id.co.edtslibcheckappversion.data.Repository
import id.co.edtslibcheckappversion.data.VersionLocalDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { VersionLocalDataSource(get()) }

    single<IRepository> { Repository(get(), get()) }
}