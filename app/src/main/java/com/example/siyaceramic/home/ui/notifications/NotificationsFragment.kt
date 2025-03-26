package com.example.siyaceramic.home.ui.notifications

import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import com.example.siyaceramic.R
import com.example.siyaceramic.base.BaseFragment
import com.example.siyaceramic.databinding.FragmentNotificationsBinding
import com.example.siyaceramic.home.ui.home.HomeFragment
import com.example.siyaceramic.home.ui.notifications.model.LogoutResponse
import com.example.siyaceramic.home.ui.notifications.model.ProfileResponse
import com.example.siyaceramic.home.ui.notifications.viewmodel.ProfileViewModel
import com.example.siyaceramic.login.LoginActivity
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.logoutAPI
import com.example.siyaceramic.network.profileAPI
import com.example.siyaceramic.util.SharedPrefCons
import com.example.siyaceramic.util.TxtUtils
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : BaseFragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private var _binding: FragmentNotificationsBinding? = null
    private val pref: SharedPreferences by inject()
    val viewModel: ProfileViewModel by viewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!
    var dialogEditUser: Dialog? = null
    var dialogEditMobile: Dialog? = null
    var dialogEditEmail: Dialog? = null
    var dialogEditCompany: Dialog? = null
    var name: String = ""
    var mobile: String = ""
    var username: String? = ""
    var mobileNum: String? = ""
    var email: String? = ""
    var company: String? = ""
    var context: FragmentActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()

    }

    private fun initView() {
        context = activity
        binding.btnLogout.setOnClickListener {
            callLogoutApi()
            /* pref.edit(commit = true) {
                 remove(SharedPrefCons.userProfileData)
                 apply()
             }
             LoginActivity.start(requireContext())
             requireActivity().finish()*/
        }

        binding.ivUserEdit.setOnClickListener {
            dialogEditUser = Dialog(requireContext())
            dialogEditUser?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogEditUser?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //this line is what you need:
            dialogEditUser?.setContentView(R.layout.row_user_dialog)
            dialogEditUser?.setCancelable(false)
            dialogEditUser?.setCanceledOnTouchOutside(false)

            val btnUpdate = dialogEditUser?.findViewById<View>(R.id.btnUpdate) as AppCompatButton
            val btnClose = dialogEditUser?.findViewById<View>(R.id.imgClose) as AppCompatImageView
            val edtUsername =
                dialogEditUser?.findViewById<View>(R.id.edtNameText) as AppCompatEditText

            edtUsername.setText(username)

            btnClose.setOnClickListener { dialogEditUser?.dismiss() }
            btnUpdate.setOnClickListener {
                if (TxtUtils.isEmptyText(edtUsername)) {
                    Toast.makeText(context, "Please Enter Username", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    name = edtUsername.text.toString()
                    binding.usernameTv.text = name
                    dialogEditUser?.dismiss()
                }
            }
            dialogEditUser?.show()
        }

        binding.ivMoEdit.setOnClickListener {
            dialogEditMobile = Dialog(requireContext())
            dialogEditMobile?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogEditMobile?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //this line is what you need:
            dialogEditMobile?.setContentView(R.layout.row_mobile_dialog)
            dialogEditMobile?.setCancelable(false)
            dialogEditMobile?.setCanceledOnTouchOutside(false)

            val btnUpdate = dialogEditMobile?.findViewById<View>(R.id.btnUpdate) as AppCompatButton
            val edtMobile =
                dialogEditMobile?.findViewById<View>(R.id.edtMobileText) as AppCompatEditText
            val btnClose = dialogEditMobile?.findViewById<View>(R.id.imgClose) as AppCompatImageView

            edtMobile.setText(mobileNum)
            btnClose.setOnClickListener { dialogEditMobile?.dismiss() }

            btnUpdate.setOnClickListener {
                if (TxtUtils.isEmptyText(edtMobile)) {
                    Toast.makeText(
                        context,
                        "Please Enter Mobile Number",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mobile = edtMobile.text.toString()
                    binding.mobileTv.text = mobile
                    dialogEditMobile?.dismiss()
                }
            }


            dialogEditMobile?.show()

        }

        binding.ivEmEdit.setOnClickListener {
            dialogEditEmail = Dialog(requireContext())
            dialogEditEmail?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogEditEmail?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //this line is what you need:
            dialogEditEmail?.setContentView(R.layout.row_email_dialog)
            dialogEditEmail?.setCancelable(false)
            dialogEditEmail?.setCanceledOnTouchOutside(false)

            val btnUpdate = dialogEditEmail?.findViewById<View>(R.id.btnUpdate) as AppCompatButton
            val edtEmail =
                dialogEditEmail?.findViewById<View>(R.id.edtEmailText) as AppCompatEditText
            val btnClose = dialogEditEmail?.findViewById<View>(R.id.imgClose) as AppCompatImageView

            edtEmail.setText(email)
            btnClose.setOnClickListener { dialogEditEmail?.dismiss() }

            btnUpdate.setOnClickListener {
                if (TxtUtils.isEmptyText(edtEmail)) {
                    Toast.makeText(
                        context,
                        "Please Enter Email Id",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    email = edtEmail.text.toString()
                    binding.emailTv.text = email
                    dialogEditEmail?.dismiss()
                }
            }

            dialogEditEmail?.show()

        }

        binding.ivCmEdit.setOnClickListener {
            dialogEditCompany = Dialog(requireContext())
            dialogEditCompany?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogEditCompany?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //this line is what you need:
            dialogEditCompany?.setContentView(R.layout.row_company_dialog)
            dialogEditCompany?.setCancelable(false)
            dialogEditCompany?.setCanceledOnTouchOutside(false)

            val btnUpdate = dialogEditCompany?.findViewById<View>(R.id.btnUpdate) as AppCompatButton
            val edtCompany =
                dialogEditCompany?.findViewById<View>(R.id.edtCompanyText) as AppCompatEditText
            val btnClose = dialogEditCompany?.findViewById<View>(R.id.imgClose) as AppCompatImageView

            edtCompany.setText(company)
            btnClose.setOnClickListener { dialogEditCompany?.dismiss() }

            btnUpdate.setOnClickListener {
                if (TxtUtils.isEmptyText(edtCompany)) {
                    Toast.makeText(
                        context,
                        "Please Enter Company Name",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    company = edtCompany.text.toString()
                    binding.companyTv.text = company
                    dialogEditCompany?.dismiss()
                }
            }

            dialogEditCompany?.show()




        }

        callApi()

    }

    private fun initObserver() {

        viewModel.getUiState().observe(requireActivity()) {
            when (it) {
                is ResourceStatus.Loading<*> -> {
                    binding.progressBar.progressBar.visibility = View.VISIBLE
                    binding.progressBar.ivCelebration.visibility = View.VISIBLE
//                    binding.progressBar.ivCelebration.playAnimation()
                }
                is ResourceStatus.Success -> {

                    binding.progressBar.progressBar.visibility = View.GONE
                    when (it.apiName) {

                        profileAPI -> {
                            val response = it.myResp as ProfileResponse
                            if (response.data != null) {
                                val stringData = Gson().toJson(response.data)
                                pref.edit(commit = true) {
                                    putString(SharedPrefCons.profileData, stringData)
                                }
                                val data = response.data
                                username = data.username
                                mobileNum = data.mobileNumber.toString()
                                company = data.company_name.toString()
                                email = data.email.toString()
                                binding.usernameTv.text = data.username
                                binding.mobileTv.text = data.mobileNumber.toString()
                                binding.companyTv.text = data.company_name.toString()
                                binding.emailTv.text = data.email.toString()

                            } else {
                                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                            }

                        }

                        logoutAPI -> {
                            val response = it.myResp as LogoutResponse
                            if (response.status == true) {

                                 pref.edit(commit = true) {
                                   remove(SharedPrefCons.userProfileData)
                                   remove(SharedPrefCons.profileData)
                                   remove(SharedPrefCons.homeProfileData)
                                    apply()
                                 }
                                LoginActivity.start(requireContext())
                                requireActivity().finish()


                            } else {
                                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                            }

                        }

                    }

                }


                is ResourceStatus.Error -> {
                    val error = it.throwable as Throwable
                    binding.progressBar.progressBar.visibility = View.GONE
                    TxtUtils.hideKeyBoard(context, binding.root)
                    when (it.apiName) {
                        profileAPI -> {
//                            Toast.makeText(context, "Invalid Data", Toast.LENGTH_SHORT).show()
                        }

                        logoutAPI -> {
//                            Toast.makeText(context, "Invalid Data", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                else -> {
                    binding.progressBar.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun callApi() {
        viewModel.getProfileApi(true)
    }

    private fun callLogoutApi() {
        viewModel.logoutUser(true)
    }

    companion object {

        @JvmStatic
        fun newInstance() = NotificationsFragment().apply {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}