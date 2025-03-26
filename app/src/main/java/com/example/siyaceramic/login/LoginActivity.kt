package com.example.siyaceramic.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.text.isDigitsOnly
import com.example.siyaceramic.R
import com.example.siyaceramic.base.BaseActivity
import com.example.siyaceramic.custom.Validator
import com.example.siyaceramic.databinding.ActivityLoginBinding
import com.example.siyaceramic.forgot.ForgotPasswordActivity
import com.example.siyaceramic.home.HomeActivity
import com.example.siyaceramic.login.model.LoginReqModel
import com.example.siyaceramic.login.model.LoginResponse
import com.example.siyaceramic.login.viewmodel.LoginViewModel
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.loginUserAPI
import com.example.siyaceramic.register.RegisterActivity
import com.example.siyaceramic.util.SharedPrefCons
import com.example.siyaceramic.util.TxtUtils
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val layout: Int = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()

    private var isShowPassword: Boolean = true
    private val pref: SharedPreferences by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initObserver()

    }

    private fun initView() {
        binding.txtForgot.setOnClickListener {
            ForgotPasswordActivity.start(this)
        }
        binding.bLogin.setOnClickListener {
            if (validate()) {
                callApi()
            }
//            Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show()
//            HomeActivity.start(this)
            /*val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()*/
        }
        val styledText = "<u><font color='#000759'>Register</font></u>"+" as a new user"
        binding.tvRegister.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE)
        binding.tvRegister.setOnClickListener {
            RegisterActivity.start(this)
            finish()

        }

        binding.ivShowPass.setOnClickListener {
            if (isShowPassword) {
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.edtPassword.setSelection(binding.edtPassword.length())
                binding.ivShowPass.setImageResource(R.drawable.ic_pass_view)
                isShowPassword = false
            } else {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.edtPassword.setSelection(binding.edtPassword.length())
                binding.edtPassword
                binding.ivShowPass.setImageResource(R.drawable.ic_pass_hide)
                isShowPassword = true
            }
        }
    }

    private fun initObserver() {
        /**
         * Handle result of API call
         */
        viewModel.getUiState().observe(this) {
            when (it) {
                is ResourceStatus.Loading<*> -> {
                    if (it.isLoaderEnable as Boolean) {
                        binding.progressBar.progressBar.visibility = View.VISIBLE
                        binding.progressBar.ivCelebration.visibility = View.VISIBLE
//                        binding.progressBar.ivCelebration.playAnimation()

                    }
                }

                is ResourceStatus.Success -> {

                    when (it.apiName) {


                        loginUserAPI -> {
                            binding.progressBar.progressBar.visibility = View.GONE
                            val response = it.myResp as LoginResponse
                            val data = response.data
                            val status = response.status
                            if (status == true) {
                                if (data != null) {

                                    val stringData = Gson().toJson(response)

                                    pref.edit(commit = true) {
                                        putString(SharedPrefCons.userProfileData, stringData)
                                        putString(SharedPrefCons.token, response.data.token.toString())
                                    }
//                                    Toast.makeText(this,""+response.data.id,Toast.LENGTH_SHORT).show()
                                    HomeActivity.start(this)
                                    finishAffinity()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Kindly register this id first",
                                    Toast.LENGTH_SHORT
                                ).show()
//                                make(binding.root, "Kindly register this id first")
                            }
                        }
                    }
                }

                is ResourceStatus.Error -> {
                    val error = it.throwable as Throwable

                    binding.progressBar.progressBar.visibility = View.GONE

                    when (it.apiName) {


                        loginUserAPI -> {
                            Toast.makeText(this, error.localizedMessage ?: "", Toast.LENGTH_SHORT)
                                .show()

//                            make(binding.root, error.localizedMessage ?: "")
                        }

                        else -> {
                            Toast.makeText(this, error.localizedMessage ?: "", Toast.LENGTH_SHORT)
                                .show()

//                            make(binding.loginRoot, error.localizedMessage ?: "")
                        }
                    }
                }
                else -> Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//make(binding.loginRoot, "Something went wrong")
            }
        }
    }

    private fun validate(): Boolean {
        TxtUtils.hideKeyBoard(this, binding.loginRoot)
        if (!isValidateUserName) {
            return false
        }
        if (!isValidatePassword) {
            return false
        }
        return true
    }

    private fun callApi() {
        val loginReqModel = LoginReqModel()
        loginReqModel.mobile_number = TxtUtils.getTextValue(binding.edtUserName)
        loginReqModel.password = TxtUtils.getTextValue(binding.edtPassword).toInt()
        viewModel.loginUser(loginReqModel, true)
    }


    private val isValidateUserName: Boolean
        get() {
            val data = binding.edtUserName.text?.trim()
            val validator = Validator()
            if (TxtUtils.isEmptyText(binding.edtUserName)) {

                Toast.makeText(this, R.string.Please_enter_username_mobile, Toast.LENGTH_SHORT)
                    .show()

                return false
            }

            if (data!!.isDigitsOnly()) {
                if (!validator.validatePhone(
                        TxtUtils.getTextValue(binding.edtUserName)
                    )
                ) {
                    Toast.makeText(this, R.string.Please_enter_valid_mobile, Toast.LENGTH_SHORT)
                        .show()
                    return false
                }
                return true
            }

            return true
        }

    private val isValidatePassword: Boolean
        get() {
            if (TxtUtils.isEmptyText(binding.edtPassword)) {

                Toast.makeText(this, R.string.Please_enter_password, Toast.LENGTH_SHORT).show()

                return false
            }
            if (binding.edtPassword.text?.length!! < 6) {

                Toast.makeText(this, R.string.Please_enter_min_password, Toast.LENGTH_SHORT).show()

                return false
            }

            return true
        }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}