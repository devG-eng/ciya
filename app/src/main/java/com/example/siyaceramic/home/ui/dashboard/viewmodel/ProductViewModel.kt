package com.example.siyaceramic.home.ui.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.siyaceramic.base.BaseVM
import com.example.siyaceramic.home.ui.dashboard.repository.ProductRepository
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.proDetailAPI
import com.example.siyaceramic.network.productdetailAPI
import com.example.siyaceramic.network.productsAPI
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : BaseVM() {
    /**
     * Used for handle UI state of API call.
     */
    private val mutableLiveData = MutableLiveData<ResourceStatus<*>>()
    fun getUiState() = mutableLiveData

    fun getProductApi(search: String,isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, productsAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(productRepository.getProData(search,productsAPI))
        }
    }

    fun getProductDetails(sizeId: Int,catId:Int,isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, productdetailAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(productRepository.getProDetail(sizeId,catId,productdetailAPI))
        }
    }

    fun getProductsDetails(catId:Int,isLoaderEnable: Boolean? = false) {
        mutableLiveData.postValue(ResourceStatus.Loading(isLoaderEnable, productsAPI))
        viewModelScope.launch {
            mutableLiveData.postValue(productRepository.getProductDetail(catId,productsAPI))
        }
    }
}