package id.co.edtslibcheckappversion.example

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentActivity
import id.co.edtslibcheckappversion.CheckAppVersion
import id.co.edtslibcheckappversion.data.CheckAppVersionDelegate
import id.co.edtslibcheckappversion.data.VersionItem

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        CheckAppVersion.debugging = true
        CheckAppVersion.path = "ANDROID"
        CheckAppVersion.init(this, "http://172.18.183.15:8081/api/mobile/app-version/",
            object : CheckAppVersionDelegate {
                override fun onLoading(fragmentActivity: FragmentActivity) {
                    Log.d("abah", "abah onLoading")
                }

                override fun onError(fragmentActivity: FragmentActivity, code: String?, message: String?) {
                    Log.d("abah", "abah onError $message")
                }

                override fun onUnAuthorize(fragmentActivity: FragmentActivity) {
                    Log.d("abah", "abah onUnAuthorize")
                }

                override fun onAppMustUpdate(
                    fragmentActivity: FragmentActivity,
                    message: String?,
                    serverVersion: VersionItem?
                ) {
                    Log.d("abah", "abah onAppMustUpdate")
                }

                override fun onAppOptionalUpdate(
                    fragmentActivity: FragmentActivity,
                    message: String?,
                    serverVersion: VersionItem?
                ) {
                    Log.d("abah", "abah  onAppOptionalUpdate")
                }

                override fun onAppVersionLatest(
                    fragmentActivity: FragmentActivity,
                    message: String?,
                    serverVersion: VersionItem?
                ) {
                    Log.d("abah", "abah onAppVersionLatest")
                }

            })
    }
}