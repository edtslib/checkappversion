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
                return VersionResponse(
                    result = VersionCompareResult.Newer,
                    message = null,
                    versionItem = null)
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
                                    return VersionResponse(
                                        result = VersionCompareResult.MustUpdate,
                                        message = versionItem.forceAlertDesc,
                                        versionItem = versionItem)
                                }
                            }

                            if (versionItem.warningAlert == true && versionItem.warningAlertDesc != null
                                && versionItem.warningAlertStartDate != null) {
                                val date = parseDate(versionItem.warningAlertStartDate)
                                val now = Date()
                                if (date != null && now.time >= date.time) {
                                    return VersionResponse(
                                        result = VersionCompareResult.Update,
                                        message = versionItem.warningAlertDesc,
                                        versionItem = versionItem)
                                }
                            }

                            if (versionItem.forceUpdate == true) {
                                return VersionResponse(
                                    result = VersionCompareResult.MustUpdate,
                                    message = versionItem.updateDesc,
                                    versionItem = versionItem)
                            }

                            if (versionItem.forceUpdate == false) {
                                return VersionResponse(
                                    result = VersionCompareResult.Update,
                                    message = versionItem.updateDesc,
                                    versionItem = versionItem)
                            }


                            return VersionResponse(
                                result = VersionCompareResult.Newer,
                                message = null,
                                versionItem = versionItem)
                        }
                        else
                            if (aver > sver) {
                                return VersionResponse(
                                    result = VersionCompareResult.Newer,
                                    message = null,
                                    versionItem = versionItem)
                            }
                    }
                    catch (e: NumberFormatException) {
                        return VersionResponse(
                            result = VersionCompareResult.MustUpdate,
                            message = "Format version salah",
                            versionItem = versionItem)
                    }
                }

            }

            return VersionResponse(
                result = VersionCompareResult.Newer,
                message = null,
                versionItem = versionItem)

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