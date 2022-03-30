package id.co.edtslibcheckappversion.data

import id.co.edtslibcheckappversion.CheckAppVersion
import kotlinx.coroutines.flow.flow
import java.util.*

class Repository(private val localDataSource: VersionLocalDataSource,
                 private val remoteDataSource: RemoteDataSource): IRepository {
    override fun get(appVersion: String) = flow {
        var cached = localDataSource.getCached()
        val interval = CheckAppVersion.intervalCached
        val isCached =
            if (cached?.lastCheck == null)
                false
            else
                Date().time <= cached.lastCheck!!+interval

        if (! isCached) {
            emit(Result.loading<VersionResponse?>())
            val response = remoteDataSource.get()
            when (response.status) {
                Result.Status.SUCCESS -> {
                    if (response.data != null && response.data.isSuccess()) {
                        if (cached == null) {
                            cached = CacheData(null, null, null)
                        }

                        val result = VersionItem.checkVersion(
                            appVersion,
                            response.data.data)

                        when {
                            "0.0.0" == response.data.data?.version -> {
                                emit(Result.success(VersionResponse(VersionCompareResult.MustUpdate, result.message)))
                            }
                            result.result == VersionCompareResult.Update -> {
                                if (response.data.data?.version == cached.lastShow) {
                                    cached.lastCheck = Date().time

                                    emit(Result.success(VersionResponse(VersionCompareResult.Newer, result.message)))
                                } else {

                                    emit(Result.success(VersionResponse(VersionCompareResult.Update, result.message)))

                                }
                            }
                            else -> {
                                if (result.result == VersionCompareResult.Newer) {
                                    cached.lastCheck = Date().time
                                }
                                emit(Result.success(VersionResponse(result.result, result.message)))
                            }
                        }

                        localDataSource.save(cached)
                    } else {
                        emit(Result.error<VersionResponse?>(response.code, response.message))
                    }
                }
                Result.Status.UNAUTHORIZED -> {
                    emit(Result.unauthorized<VersionResponse?>())
                }
                else -> {
                    emit(Result.error<VersionResponse?>(response.code, response.message))
                }
            }
        }
        else {
            emit(Result.success(VersionResponse(VersionCompareResult.Newer, null)))
        }
    }
}