package com.example.siyaceramic.home.ui.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.siyaceramic.base.BaseVM
import com.example.siyaceramic.home.ui.dashboard.repository.CategoryRepository
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.catAPI
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : BaseVM() {
    /**
     * Used for handle UI state of API call.
     */
    private val mutableLiveData = MutableLiveData<ResourceStatus<*>>()
    fun getUiState() = mutableLiveData

    fun getCatApi(isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, catAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(categoryRepository.getCatData(catAPI))
        }
    }
}