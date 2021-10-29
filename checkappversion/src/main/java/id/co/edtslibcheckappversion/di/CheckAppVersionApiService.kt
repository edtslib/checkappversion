package id.co.edtslibcheckappversion.di

import id.co.edtslibcheckappversion.data.ApiResponse
import id.co.edtslibcheckappversion.data.VersionItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CheckAppVersionApiService {

    @GET("{path}")
    suspend fun get(@Path("path") path:String): Response<ApiResponse<VersionItem>>

}