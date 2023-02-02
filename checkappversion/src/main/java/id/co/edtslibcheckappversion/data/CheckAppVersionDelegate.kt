package id.co.edtslibcheckappversion.data

import androidx.fragment.app.FragmentActivity

interface CheckAppVersionDelegate {
    fun onLoading(fragmentActivity: FragmentActivity)
    fun onError(fragmentActivity: FragmentActivity, code: String?, message: String?)
    fun onUnAuthorize(fragmentActivity: FragmentActivity)
    fun onAppMustUpdate(fragmentActivity: FragmentActivity, message: String?,
                        serverVersion: VersionItem?)
    fun onAppOptionalUpdate(fragmentActivity: FragmentActivity, message: String?,
                            serverVersion: VersionItem?)
    fun onAppVersionLatest(fragmentActivity: FragmentActivity, message: String?,
                           serverVersion: VersionItem?, callback: () -> Unit)
}