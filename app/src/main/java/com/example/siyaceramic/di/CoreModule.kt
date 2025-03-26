package com.example.siyaceramic.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Used to define android code object declaration.
 */
val coreModule = module {
    single {
        androidApplication().getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    }
}