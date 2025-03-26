package com.example.siyaceramic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.siyaceramic.base.BaseActivity
import com.example.siyaceramic.databinding.ActivityMainBinding
import com.example.siyaceramic.login.LoginActivity
import com.example.siyaceramic.register.RegisterActivity
import com.example.siyaceramic.splash.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding,SplashViewModel>() {
    override val layout: Int
        get() = R.layout.activity_main
    override val viewModel: SplashViewModel by viewModel()
    private var bRegister: AppCompatButton? = null
    var bLogin: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        bRegister = findViewById<AppCompatButton>(R.id.btnRegister)
        bLogin = findViewById<AppCompatButton>(R.id.btnLogin)

        bRegister?.setOnClickListener {
//            Toast.makeText(this,"Register",Toast.LENGTH_SHORT).show()
            RegisterActivity.start(this)
        }

        bLogin?.setOnClickListener {
//            Toast.makeText(this,"Login",Toast.LENGTH_SHORT).show()
            LoginActivity.start(this)
        }
    }


    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}