package id.co.edtslibcheckappversion.data

import java.lang.NumberFormatException

data class VersionItem(
    val version: String,
    val forceUpdate: Boolean?
) {
    companion object {
        fun checkVersion(app:String, versionItem: VersionItem?): VersionCompareResult {
            if (versionItem == null)
            {
                return VersionCompareResult.Newer
            }

            // make version to be x.x.x
            val serverVersions = makeVersion(versionItem.version)
            val appVersions = makeVersion(app)

            if (serverVersions.size == appVersions.size) {
                for (i in serverVersions.indices) {
                    try {
                        val sver = serverVersions[i].toInt()
                        val aver = appVersions[i].toInt()
                        if (aver < sver) {
                            return if (versionItem.forceUpdate != null && versionItem.forceUpdate) VersionCompareResult.MustUpdate
                            else VersionCompareResult.Update
                        }
                        else
                            if (aver > sver) {
                                return VersionCompareResult.Newer
                            }
                    }
                    catch (e: NumberFormatException) {
                        VersionCompareResult.MustUpdate
                    }
                }

            }

            return VersionCompareResult.Newer

        }

        // buat versi menjadi x.x.x
        private fun makeVersion(ver: String): List<String> {
            val verList = ver.split('.')

            val result = mutableListOf<String>()
            while (result.size < 3) {
                if (result.size < verList.size) {
                    val s = verList[result.size]
                    result.add(s)
                }
                else {
                    result.add("0")
                }
            }

            return result
        }
    }
}