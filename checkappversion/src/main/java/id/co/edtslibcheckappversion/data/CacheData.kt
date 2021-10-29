package id.co.edtslibcheckappversion.data

data class CacheData (
    val version: VersionItem?,
    var lastCheck: Long?,
    var lastShow: String?
)