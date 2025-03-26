package com.example.siyaceramic.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.siyaceramic.BR



abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseVM> : AppCompatActivity() {

    lateinit var binding: VDB
    abstract val layout: Int
    abstract val viewModel: VM

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
//        if(this is DialogActivity)
//        {
//            setTheme(R.style.Theme_Transparent)
//        }
        super.onCreate(savedInstanceState)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        /**
         * Binding and set viewmodel variable of activity.
         * set lifecycle owner.
         */
        binding = DataBindingUtil.setContentView(this, layout)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }




}