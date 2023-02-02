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
            emit(Result.loading())
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
                                localDataSource.clear()
                                emit(Result.success(VersionResponse(
                                    result = VersionCompareResult.MustUpdate,
                                    message = result.message,
                                    versionItem = response.data.data)))
                            }
                            result.result == VersionCompareResult.Update -> {
                                if (response.data.data?.version == cached.lastShow) {
                                    cached.lastCheck = Date().time

                                    localDataSource.save(cached)
                                    emit(Result.success(VersionResponse(
                                        result = VersionCompareResult.Newer,
                                        message = result.message,
                                        versionItem = response.data.data)))
                                } else {

                                    localDataSource.save(cached)
                                    emit(Result.success(VersionResponse(
                                        result = VersionCompareResult.Update,
                                        message = result.message,
                                        versionItem = response.data.data)))

                                }
                            }
                            else -> {
                                if (result.result == VersionCompareResult.Newer) {
                                    cached.lastCheck = Date().time
                                    localDataSource.save(cached)
                                }
                                else {
                                    localDataSource.clear()
                                }
                                emit(Result.success(VersionResponse(
                                    result = result.result,
                                    message = result.message,
                                    versionItem = response.data.data)))
                            }
                        }
                    } else {
                        emit(Result.error(response.code, response.message))
                    }
                }
                Result.Status.UNAUTHORIZED -> {
                    emit(Result.unauthorized())
                }
                else -> {
                    emit(Result.error(response.code, response.message))
                }
            }
        }
        else {
            emit(Result.success(VersionResponse(
                result = VersionCompareResult.Newer,
                message = null,
                versionItem = cached?.version)))
        }
    }
}