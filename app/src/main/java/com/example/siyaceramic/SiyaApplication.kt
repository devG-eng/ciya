package com.example.siyaceramic

import android.app.Application
import com.example.siyaceramic.di.coreModule
import com.example.siyaceramic.di.networkModule
import com.example.siyaceramic.di.repositoryModule
import com.example.siyaceramic.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SiyaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        /* if (BuildConfig.BUILD_TYPE == "release")
             Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)*/


        startKoin {
            androidContext(this@SiyaApplication)
            modules(listOf(coreModule, networkModule, repositoryModule, viewModelModule))
        }

    }
}