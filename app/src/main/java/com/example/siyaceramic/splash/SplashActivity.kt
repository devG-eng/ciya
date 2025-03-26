package com.example.siyaceramic.splash

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.siyaceramic.MainActivity
import com.example.siyaceramic.R
import com.example.siyaceramic.base.BaseActivity
import com.example.siyaceramic.databinding.ActivitySplashBinding
import com.example.siyaceramic.home.HomeActivity
import com.example.siyaceramic.login.LoginActivity
import com.example.siyaceramic.login.model.LoginResponse
import com.example.siyaceramic.splash.viewmodel.SplashViewModel
import com.example.siyaceramic.util.SharedPrefCons
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override val layout: Int
        get() = R.layout.activity_splash
    private val pref: SharedPreferences by inject()
    var userid = 0

    override val viewModel: SplashViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

    }
    private fun initView(){

        val userProfileDataFromPref = pref.getString(SharedPrefCons.userProfileData, null)

        if (!userProfileDataFromPref.isNullOrEmpty()) {
            /**
             * when user come from login have profile data
             */
            val userProfileData =
                Gson().fromJson(userProfileDataFromPref, LoginResponse::class.java)
            userid = userProfileData.data?.id ?: 0
        }
//        Toast.makeText(this,"USERID"+userid, Toast.LENGTH_SHORT).show()

        if (userid == 0) {

            MainActivity.start(this)
            finish()

        } else {
            HomeActivity.start(this)
            finishAffinity()
        }
/*
        Handler().postDelayed({
            MainActivity.start(this)
            finish()
        }, 2000)*/
    }
}