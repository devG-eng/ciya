package com.example.siyaceramic.splash.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.siyaceramic.base.BaseVM
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.splash.repository.SplashRepository

class SplashViewModel(private val splashRepository: SplashRepository) : BaseVM() {
    private val mutableLiveData = MutableLiveData<ResourceStatus<*>>()

    /**
     * Get API call state.
     */
    fun getUiState() = mutableLiveData

}