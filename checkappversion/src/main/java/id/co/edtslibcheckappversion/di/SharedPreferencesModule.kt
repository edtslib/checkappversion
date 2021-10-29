package id.co.edtslibcheckappversion.di

import android.preference.PreferenceManager
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single {
        PreferenceManager.getDefaultSharedPreferences(get())
    }
}