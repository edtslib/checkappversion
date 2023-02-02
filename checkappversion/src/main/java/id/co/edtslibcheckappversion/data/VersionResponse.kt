package id.co.edtslibcheckappversion.data

data class VersionResponse(
    val result: VersionCompareResult,
    val message: String?,
    val versionItem: VersionItem?
)
