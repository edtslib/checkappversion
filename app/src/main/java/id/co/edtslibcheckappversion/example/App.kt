package id.co.edtslibcheckappversion.example

import android.app.Application
import android.util.Log
import id.co.edtslibcheckappversion.CheckAppVersion
import id.co.edtslibcheckappversion.data.CheckAppVersionDelegate

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        CheckAppVersion.debugging = true
        CheckAppVersion.init(this, "http://172.18.185.110:32080/configuration/api2506/mobile/sy-app-version/get-version-key/ANDROID/",
            object : CheckAppVersionDelegate {
                override fun onLoading() {
                    Log.d("abah", "abah onLoading")
                }

                override fun onError(code: String?, message: String?) {
                    Log.d("abah", "abah onError $message")
                }

                override fun onUnAuthorize() {
                    Log.d("abah", "abah onUnAuthorize")
                }

                override fun onAppMustUpdate() {
                    Log.d("abah", "abah onAppMustUpdate")
                }

                override fun onAppOptionalUpdate() {
                    Log.d("abah", "abah onAppOptionalUpdate")
                }

                override fun onAppVersionLatest() {
                    Log.d("abah", "abah onAppVersionLatest")
                }
            })
    }
}