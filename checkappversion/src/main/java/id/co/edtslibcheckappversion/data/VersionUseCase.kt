package id.co.edtslibcheckappversion.data

import kotlinx.coroutines.flow.Flow

interface VersionUseCase {
    fun get(appVersion: String): Flow<Result<VersionCompareResult?>>
}