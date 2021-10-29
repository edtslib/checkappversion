package id.co.edtslibcheckappversion.di

import id.co.edtslibcheckappversion.data.VersionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { VersionViewModel(get()) }
}