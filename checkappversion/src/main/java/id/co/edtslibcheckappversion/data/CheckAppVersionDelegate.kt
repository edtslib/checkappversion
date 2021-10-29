package id.co.edtslibcheckappversion.data

interface CheckAppVersionDelegate {
    fun onLoading()
    fun onError(code: String?, message: String?)
    fun onUnAuthorize()
    fun onAppMustUpdate()
    fun onAppOptionalUpdate()
    fun onAppVersionLatest()
}