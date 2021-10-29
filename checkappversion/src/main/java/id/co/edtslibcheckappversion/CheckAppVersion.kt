package id.co.edtslibcheckappversion

import android.app.Application
import androidx.fragment.app.FragmentActivity
import id.co.edtslibcheckappversion.data.CheckAppVersionDelegate
import id.co.edtslibcheckappversion.data.Result
import id.co.edtslibcheckappversion.data.VersionCompareResult
import id.co.edtslibcheckappversion.data.VersionViewModel
import id.co.edtslibcheckappversion.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import java.lang.Exception

class CheckAppVersion private constructor(): KoinComponent {
    private val viewModel: VersionViewModel? by inject()

    companion object {
        private var instance: CheckAppVersion? = null

        var endPoint = ""
        var debugging = false
        var intervalCached: Int = 3600*10000
        private var delegate: CheckAppVersionDelegate? = null

        fun init(application: Application, endPoint: String,
                 delegate: CheckAppVersionDelegate) {
            CheckAppVersion.endPoint = endPoint
            CheckAppVersion.delegate = delegate

            startKoin {
                androidContext(application.applicationContext)
                modules(
                    listOf(
                        networkingModule,
                        sharedPreferencesModule,
                        mainAppModule,
                        repositoryModule,
                        interactorModule,
                        viewModule
                    )
                )
            }

            if (instance == null) {
                instance = CheckAppVersion()
            }
        }

        fun init(baseUrl: String, koin: KoinApplication,
                 delegate: CheckAppVersionDelegate) {
            CheckAppVersion.endPoint = baseUrl
            CheckAppVersion.delegate = delegate

            koin.modules(listOf(
                networkingModule,
                sharedPreferencesModule,
                mainAppModule,
                repositoryModule,
                interactorModule,
                viewModule
            ))

            if (instance == null) {
                instance = CheckAppVersion()
            }
        }

        fun check(activity: FragmentActivity) {
            try {
                val info = activity.packageManager.getPackageInfo(activity.packageName, 0)
                check(activity, info.versionName)
            }
            catch (e: Exception) {
                check(activity, "0.0.0")
            }

        }

        fun check(activity: FragmentActivity, appVersion: String) {
            instance?.viewModel?.get(appVersion)?.observe(activity, {
                when(it.status) {
                    Result.Status.LOADING -> {
                        delegate?.onLoading()
                    }
                    Result.Status.ERROR -> delegate?.onError(it.code, it.message)
                    Result.Status.UNAUTHORIZED -> delegate?.onUnAuthorize()
                    Result.Status.SUCCESS -> {
                        when(it.data) {
                            VersionCompareResult.MustUpdate -> delegate?.onAppMustUpdate()
                            VersionCompareResult.Update -> delegate?.onAppOptionalUpdate()
                            VersionCompareResult.Newer -> delegate?.onAppVersionLatest()
                        }
                    }
                }
            })
        }

    }
}