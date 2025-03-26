package com.example.siyaceramic.di

import com.example.siyaceramic.home.ui.dashboard.repository.CategoryRepository
import com.example.siyaceramic.home.ui.dashboard.repository.ProductRepository
import com.example.siyaceramic.home.ui.home.model.HomeResponse
import com.example.siyaceramic.home.ui.home.repository.HomeRepository
import com.example.siyaceramic.home.ui.notifications.repository.ProfileRepository
import com.example.siyaceramic.login.repository.LoginRepository
import com.example.siyaceramic.register.repository.RegisterRepository
import com.example.siyaceramic.splash.repository.SplashRepository
import org.koin.dsl.module

/**
 * Used for define repository
 */
val repositoryModule = module {


    single{
        SplashRepository(get())
    }

    single{
        RegisterRepository(get())
    }

    single {
        LoginRepository(get())
    }

    single {
        HomeRepository(get())
    }

    single {
        CategoryRepository(get())
    }

    single {
        ProductRepository(get())
    }

    single {
        ProfileRepository(get())
    }
}