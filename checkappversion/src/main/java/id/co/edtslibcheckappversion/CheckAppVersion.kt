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
import javax.net.ssl.TrustManagerFactory

class CheckAppVersion private constructor(): KoinComponent {
    private val viewModel: VersionViewModel? by inject()

    companion object {
        var sslDomain = ""
        var sslPinner = ""
        var trustManagerFactory: TrustManagerFactory? = null

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
            endPoint = baseUrl
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

        fun check(activity: FragmentActivity, latestCallback: () -> Unit) {
            try {
                val info = activity.packageManager.getPackageInfo(activity.packageName, 0)
                check(activity, info.versionName, latestCallback)
            }
            catch (e: Exception) {
                check(activity, "0.0.0", latestCallback)
            }

        }

        fun check(activity: FragmentActivity, appVersion: String, latestCallback: () -> Unit) {
            instance?.viewModel?.get(appVersion)?.observe(activity) {
                when (it.status) {
                    Result.Status.LOADING -> {
                        delegate?.onLoading(activity)
                    }
                    Result.Status.ERROR -> delegate?.onError(activity, it.code, it.message)
                    Result.Status.UNAUTHORIZED -> delegate?.onUnAuthorize(activity)
                    Result.Status.SUCCESS -> {
                        when (it.data?.result) {
                            VersionCompareResult.MustUpdate -> delegate?.onAppMustUpdate(activity,
                                it.data.message, it.data.versionItem)
                            VersionCompareResult.Update -> delegate?.onAppOptionalUpdate(activity,
                                it.data.message, it.data.versionItem)
                            VersionCompareResult.Newer -> {
                                delegate?.onAppVersionLatest(
                                    activity,
                                    it.data.message, it.data.versionItem
                                )
                                latestCallback()
                            }
                        }
                    }
                }
            }
        }

    }
}