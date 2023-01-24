package id.co.edtslibcheckappversion.data

import com.google.gson.annotations.SerializedName
import java.lang.NumberFormatException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class VersionItem(
    @field:SerializedName("version")
    val version: String,

    @field:SerializedName("warningAlert")
    val warningAlert: Boolean?,

    @field:SerializedName("warningAlertStartDate")
    val warningAlertStartDate: String?,

    @field:SerializedName("warningAlertDesc")
    val warningAlertDesc: String?,

    @field:SerializedName("forceAlert")
    val forceAlert: Boolean?,

    @field:SerializedName("forceAlertStartDate")
    val forceAlertStartDate: String?,

    @field:SerializedName("forceAlertDesc")
    val forceAlertDesc: String?,

    @field:SerializedName("forceUpdate")
    val forceUpdate: Boolean?,

    @field:SerializedName("updateDesc")
    val updateDesc: String?

) {
    companion object {
        fun checkVersion(app:String, versionItem: VersionItem?): VersionResponse {
            if (versionItem == null)
            {
                return VersionResponse(VersionCompareResult.Newer, null)
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
                            if (versionItem.forceAlert == true && versionItem.forceAlertDesc != null
                                && versionItem.forceAlertStartDate != null) {
                                val date = parseDate(versionItem.forceAlertStartDate)
                                val now = Date()
                                if (date != null && now.time >= date.time) {
                                    return VersionResponse(VersionCompareResult.MustUpdate,
                                        versionItem.forceAlertDesc)
                                }
                            }

                            if (versionItem.warningAlert == true && versionItem.warningAlertDesc != null
                                && versionItem.warningAlertStartDate != null) {
                                val date = parseDate(versionItem.warningAlertStartDate)
                                val now = Date()
                                if (date != null && now.time >= date.time) {
                                    return VersionResponse(VersionCompareResult.Update,
                                        versionItem.warningAlertDesc)
                                }
                            }

                            if (versionItem.forceUpdate == true) {
                                return VersionResponse(VersionCompareResult.MustUpdate,
                                    versionItem.updateDesc)
                            }

                            if (versionItem.forceUpdate == false) {
                                return VersionResponse(VersionCompareResult.Update,
                                    versionItem.updateDesc)
                            }


                            return VersionResponse(VersionCompareResult.Newer, null)
                        }
                        else
                            if (aver > sver) {
                                return VersionResponse(VersionCompareResult.Newer, null)
                            }
                    }
                    catch (e: NumberFormatException) {
                        return VersionResponse(VersionCompareResult.MustUpdate, "Format version salah")
                    }
                }

            }

            return VersionResponse(VersionCompareResult.Newer, null)

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

        private fun parseDate(date: String): Date? {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault())
            return try {
                dateFormat.parse(date)
            }
            catch (e: ParseException) {
                null
            }
        }
    }
}