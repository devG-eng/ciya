package com.example.siyaceramic.di


import com.example.siyaceramic.home.ui.dashboard.viewmodel.CategoryViewModel
import com.example.siyaceramic.home.ui.dashboard.viewmodel.ProductViewModel
import com.example.siyaceramic.home.ui.home.viewmodel.HomeViewModel
import com.example.siyaceramic.home.ui.notifications.viewmodel.ProfileViewModel
import com.example.siyaceramic.login.viewmodel.LoginViewModel
import com.example.siyaceramic.register.viewmodel.RegisterViewModel
import com.example.siyaceramic.splash.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        SplashViewModel(get())
    }
    viewModel {
        RegisterViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        CategoryViewModel(get())
    }
    viewModel {
        ProductViewModel(get())
    }
    viewModel {
        ProfileViewModel(get())
    }
}