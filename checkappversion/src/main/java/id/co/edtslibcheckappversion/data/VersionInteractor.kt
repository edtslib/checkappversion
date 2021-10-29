package id.co.edtslibcheckappversion.data

class VersionInteractor(private val repository: IRepository): VersionUseCase {
    override fun get(appVersion: String) = repository.get(appVersion)
}