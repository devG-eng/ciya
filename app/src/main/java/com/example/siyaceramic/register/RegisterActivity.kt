package com.example.siyaceramic.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.siyaceramic.R
import com.example.siyaceramic.base.BaseActivity
import com.example.siyaceramic.custom.Validator
import com.example.siyaceramic.databinding.ActivityRegisterBinding
import com.example.siyaceramic.login.LoginActivity
import com.example.siyaceramic.network.APIResponse
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.regUserAPI
import com.example.siyaceramic.register.model.RegisterReqModel
import com.example.siyaceramic.register.model.RegisterResponse
import com.example.siyaceramic.register.viewmodel.RegisterViewModel
import com.example.siyaceramic.util.TxtUtils
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    override val layout: Int = R.layout.activity_register
    override val viewModel: RegisterViewModel by viewModel()
    private var isShowPassword: Boolean = true
    private var isShowPass: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObserver()

    }

    private fun initView() {

        binding.btnRegister.setOnClickListener {
            if (validate()) {
                 callApi()
            }
        }
        binding.ivShowPassword.setOnClickListener {
            if (isShowPassword) {
                binding.edtCreatePwd.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.edtCreatePwd.setSelection(binding.edtCreatePwd.length())
                binding.ivShowPassword.setImageResource(R.drawable.ic_pass_view)
                isShowPassword = false
            } else {
                binding.edtCreatePwd.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.edtCreatePwd.setSelection(binding.edtCreatePwd.length())
                binding.edtCreatePwd
                binding.ivShowPassword.setImageResource(R.drawable.ic_pass_hide)
                isShowPassword = true
            }
        }

        binding.ivShowPass.setOnClickListener {
            if (isShowPass) {
                binding.edtConfirmPwd.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.edtConfirmPwd.setSelection(binding.edtConfirmPwd.length())
                binding.ivShowPass.setImageResource(R.drawable.ic_pass_view)
                isShowPass = false
            } else {
                binding.edtConfirmPwd.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.edtConfirmPwd.setSelection(binding.edtConfirmPwd.length())
                binding.edtConfirmPwd
                binding.ivShowPass.setImageResource(R.drawable.ic_pass_hide)
                isShowPass = true
            }
        }

        binding.tvLogin.setOnClickListener {
            LoginActivity.start(this)
            finish()
        }


    }

    private fun callApi() {
        val registerReqModel = RegisterReqModel()
        registerReqModel.username = TxtUtils.getTextValue(binding.edtUsername)
        registerReqModel.company_name = TxtUtils.getTextValue(binding.edtCompany)
        registerReqModel.email = TxtUtils.getTextValue(binding.edtEmail)
        registerReqModel.mobile_number = TxtUtils.getTextValue(binding.edtMobile)
        registerReqModel.password = TxtUtils.getTextValue(binding.edtCreatePwd)
        registerReqModel.confirm_password = TxtUtils.getTextValue(binding.edtConfirmPwd)
        viewModel.registerUser(registerReqModel, true)

    }

    private fun validate(): Boolean {
        TxtUtils.hideKeyBoard(this, binding.regRoot)
        if (!isValidateUserName) {
            return false
        }
        if (!isValidatePhone) {
            return false
        }
        if (!isValidateCompanyName) {
            return false
        }
        if (!isValidateEmail) {
            return false
        }
        if (!isValidatePassword) {
            return false
        }
        if (!isValidateCpassWord) {
            return false
        }
        return true
    }

    private val isValidatePhone: Boolean
        get() {
            val validator = Validator()
            if (TxtUtils.isEmptyText(binding.edtMobile)) {

                Toast.makeText(this, R.string.Please_enter_phone, Toast.LENGTH_SHORT).show()

                return false
            }
            if (!validator.validatePhone(
                    TxtUtils.getTextValue(binding.edtMobile)
                )
            ) {
                Toast.makeText(this, R.string.Please_enter_valid_mobile, Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }

    private val isValidateUserName: Boolean
        get() {
            if (TxtUtils.isEmptyText(binding.edtUsername)) {

                Toast.makeText(this, R.string.Please_enter_username, Toast.LENGTH_SHORT).show()

                return false
            }

            return true
        }

    private val isValidateCompanyName: Boolean
        get() {
            if (TxtUtils.isEmptyText(binding.edtCompany)) {

                Toast.makeText(this, R.string.Please_enter_company, Toast.LENGTH_SHORT).show()

                return false
            }

            return true
        }

    private val isValidateEmail: Boolean
        get() {
            val validator = Validator()

            if (TxtUtils.isEmptyText(binding.edtEmail)) {

                Toast.makeText(this, R.string.Please_enter_email, Toast.LENGTH_SHORT).show()

                return false
            }
            if (!validator.validateEmail(
                    TxtUtils.getTextValue(binding.edtEmail)
                )
            ) {
                Toast.makeText(this, R.string.Please_enter_valid_email, Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }

    private val isValidatePassword: Boolean
        get() {
            if (TxtUtils.isEmptyText(binding.edtCreatePwd)) {

                Toast.makeText(this, R.string.Please_enter_password, Toast.LENGTH_SHORT).show()

                return false
            }
            if (binding.edtCreatePwd.text?.length!! < 6) {

                Toast.makeText(this, R.string.Please_enter_min_password, Toast.LENGTH_SHORT).show()

                return false
            }

            return true
        }

    private val isValidateCpassWord: Boolean
        get() {

            if (TxtUtils.isEmptyText(binding.edtConfirmPwd)) {
                Toast.makeText(this, R.string.Please_enter_confirm_password, Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            if (binding.edtConfirmPwd.text?.length!! < 6) {
                Toast.makeText(this, R.string.Please_enter_confirm_min_password, Toast.LENGTH_SHORT).show()

                return false
            }

            if (TxtUtils.getTextValue(binding.edtCreatePwd) != TxtUtils.getTextValue(
                    binding.edtConfirmPwd
                )
            ) {
                Toast.makeText(this, R.string.password_miss_match, Toast.LENGTH_SHORT).show()
                return false
            }
            return true
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


                        regUserAPI -> {
                            binding.progressBar.progressBar.visibility = View.GONE
                            val response = it.myResp as RegisterResponse
                            val data = response.data
                            val status = response.status
                            Log.d("Register", "Response" + response)
                            if (status == true) {
                                   Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT)
                                        .show()

                                    LoginActivity.start(this)
                                    finishAffinity()
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


                        regUserAPI -> {
                            Log.d("ERROR",error.localizedMessage)
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

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }
}