package com.example.siyaceramic.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.siyaceramic.base.BaseVM
import com.example.siyaceramic.login.model.LoginReqModel
import com.example.siyaceramic.login.repository.LoginRepository
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.loginUserAPI
import kotlinx.coroutines.launch

class LoginViewModel (private val loginRepository: LoginRepository)  : BaseVM() {
    /**
     * Used for handle UI state of API call.
     */
    private val mutableLiveData = MutableLiveData<ResourceStatus<*>>()
    fun getUiState() = mutableLiveData

    /**
     * USed for call Login API call.
     */
    fun loginUser(loginReqModel: LoginReqModel, isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, loginUserAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(loginRepository.loginUser(loginReqModel, loginUserAPI))
        }
    }
}