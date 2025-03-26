package com.example.siyaceramic.login.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginReqModel(

    @field:SerializedName("mobile_number")
    var mobile_number: String? = null,
    @field:SerializedName("password")
    var password: Int? = null

): Parcelable
