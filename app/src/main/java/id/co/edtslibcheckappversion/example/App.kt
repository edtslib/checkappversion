package id.co.edtslibcheckappversion.example

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentActivity
import id.co.edtslibcheckappversion.CheckAppVersion
import id.co.edtslibcheckappversion.data.CheckAppVersionDelegate

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        CheckAppVersion.debugging = true
        CheckAppVersion.init(this, "http://gurihmas-api.sg-edts.co.id/app/version/android/",
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

                override fun onAppMustUpdate(fragmentActivity: FragmentActivity, message: String?) {
                    Log.d("abah", "abah onAppMustUpdate")
                }

                override fun onAppOptionalUpdate(fragmentActivity: FragmentActivity, message: String?) {
                    Log.d("abah", "abah onAppOptionalUpdate")
                }

                override fun onAppVersionLatest(fragmentActivity: FragmentActivity, message: String?) {
                    Log.d("abah", "abah onAppVersionLatest")
                }
            })
    }
}