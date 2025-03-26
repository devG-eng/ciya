package com.example.siyaceramic.register.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterReqModel (
    @field:SerializedName("username")
    var username: String? = null,

    @field:SerializedName("company_name")
    var company_name: String? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("mobile_number")
    var mobile_number: String? = null,

    @field:SerializedName("password")
    var password: String? = null,

    @field:SerializedName("confirm_password")
    var confirm_password: String? = null

    ) : Parcelable