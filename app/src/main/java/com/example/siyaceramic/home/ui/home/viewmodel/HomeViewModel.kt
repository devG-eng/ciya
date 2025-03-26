package com.example.siyaceramic.home.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.siyaceramic.base.BaseVM
import com.example.siyaceramic.home.ui.home.repository.HomeRepository
import com.example.siyaceramic.network.*
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository) : BaseVM() {
    /**
     * Used for handle UI state of API call.
     */
    private val mutableLiveData = MutableLiveData<ResourceStatus<*>>()
    fun getUiState() = mutableLiveData

    fun getHomeApi(isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, homeAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(homeRepository.getHomeData(homeAPI))
        }
    }

    fun getCatApi(sizeId: Int,isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, catSizeAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(homeRepository.getCatData(sizeId, catSizeAPI))
        }
    }

}