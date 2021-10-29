package id.co.edtslibcheckappversion.di

import id.co.edtslibcheckappversion.data.VersionInteractor
import id.co.edtslibcheckappversion.data.VersionUseCase
import org.koin.dsl.module

val interactorModule = module {
    factory<VersionUseCase> { VersionInteractor(get()) }
}