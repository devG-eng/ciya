package com.example.siyaceramic.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.siyaceramic.base.BaseVM
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.regUserAPI
import com.example.siyaceramic.register.model.RegisterReqModel
import com.example.siyaceramic.register.repository.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerRepository: RegisterRepository)  : BaseVM() {
    /**
     * Used for handle UI state of API call.
     */
    private val mutableLiveData = MutableLiveData<ResourceStatus<*>>()
    fun getUiState() = mutableLiveData

    /**
     * USed for call Register API call.
     */
    fun registerUser(registerReqModel: RegisterReqModel, isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, regUserAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(registerRepository.registerUser(registerReqModel, regUserAPI))
        }
    }
}