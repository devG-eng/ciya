package com.example.siyaceramic.base

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.siyaceramic.home.ui.home.model.UserProfile
import com.example.siyaceramic.home.ui.notifications.model.ProfileData
import com.example.siyaceramic.login.model.LoginData
import com.example.siyaceramic.login.model.LoginResponse
import com.example.siyaceramic.util.SharedPrefCons
import com.google.gson.Gson
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseVM : ViewModel(), KoinComponent {
    private val pre: SharedPreferences by inject()

    fun getUserProfileData(): LoginResponse? {
        val userProfileDataFromPref = pre.getString(SharedPrefCons.userProfileData, "")
        return Gson().fromJson(userProfileDataFromPref, LoginResponse::class.java)
    }

    fun getUserData(): UserProfile? {
        val userProfileDataFromPref = pre.getString(SharedPrefCons.homeProfileData, "")
        val data = Gson().fromJson(userProfileDataFromPref, UserProfile::class.java)
        return data
    }


}