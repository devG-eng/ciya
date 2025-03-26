package com.example.siyaceramic.di

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import com.example.siyaceramic.BuildConfig
import com.example.siyaceramic.network.ApiService
import com.example.siyaceramic.util.SharedPrefCons.token
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Used for define all network related application level objects
 */
val networkModule = module {

    // Dependency: HttpLoggingInterceptor
    single<Interceptor> {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            @SuppressLint("LogNotTimber")
            override fun log(message: String) {
                //Log.e("OkHttp", message)
                Timber.tag("OkHttp").e(message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // get Token at run time
    factory {
        val shared = get() as SharedPreferences
        shared.getString(token, "") ?: ""
    }

    // Dependency: OkHttpClient
    single {
        OkHttpClient.Builder()
            .connectTimeout(260, TimeUnit.SECONDS)
            .readTimeout(260, TimeUnit.SECONDS)
            .writeTimeout(260, TimeUnit.SECONDS)
            .addInterceptor(get<Interceptor>())
            .addInterceptor { chain ->
                val token: String = get()
                Log.e("Auth","Token is :: $token")
                val original: Request = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Authorization",  "Bearer "+ token)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()
    }

    // Dependency: Retrofit
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Dependency: ApiService
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ApiService::class.java)
    }

}