package id.co.edtslibcheckappversion.data

import id.co.edtslibcheckappversion.di.CheckAppVersionApiService

class RemoteDataSource(
    private val apiService: CheckAppVersionApiService
) : BaseDataSource() {

    suspend fun get() =
        getResult { apiService.get("") }

}