package com.example.siyaceramic.home.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.siyaceramic.R
import com.example.siyaceramic.base.BaseActivity
import com.example.siyaceramic.databinding.ActivityCartBinding
import com.example.siyaceramic.databinding.ActivityImageViewBinding
import com.example.siyaceramic.splash.viewmodel.SplashViewModel
import com.example.siyaceramic.util.ZoomClass
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageViewActivity : BaseActivity<ActivityImageViewBinding, SplashViewModel>() {
    override val layout: Int
        get() = R.layout.activity_image_view
    override val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_image_view)
        val url=intent.getStringExtra("url")
        Glide.with(this).load(url).into(binding.largeImage)
        ZoomClass.NONE
        binding.imgClose.setOnClickListener {
            finish()
        }
    }
}