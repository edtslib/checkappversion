package id.co.edtslibcheckappversion.data

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("timestamp")
    val timeStamp: String
) {
    fun isSuccess() = "00" == status || "01" == status
    fun errorCode() = if ("02" == status || "03" == status) message else status
}