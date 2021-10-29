package id.co.edtslibcheckappversion.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class VersionViewModel(
    private val useCase: VersionUseCase
): ViewModel() {
    fun get(appVersion: String) = useCase.get(appVersion).asLiveData()
}