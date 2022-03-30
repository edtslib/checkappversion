package id.co.edtslibcheckappversion.data

import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun get(appVersion: String): Flow<Result<VersionResponse?>>

}