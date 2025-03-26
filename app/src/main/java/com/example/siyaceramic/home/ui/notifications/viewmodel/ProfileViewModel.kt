package com.example.siyaceramic.home.ui.notifications.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.siyaceramic.base.BaseVM
import com.example.siyaceramic.home.ui.notifications.repository.ProfileRepository
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.logoutAPI
import com.example.siyaceramic.network.profileAPI
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : BaseVM()  {
    /**
     * Used for handle UI state of API call.
     */
    private val mutableLiveData = MutableLiveData<ResourceStatus<*>>()
    fun getUiState() = mutableLiveData

    fun getProfileApi(isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, profileAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(profileRepository.getProfileData(profileAPI))
        }
    }

    fun logoutUser(isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, logoutAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(profileRepository.logoutData(logoutAPI))
        }
    }


}