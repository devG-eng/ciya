package com.example.siyaceramic.login.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
	@field:SerializedName("data")
	val data: LoginData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null

) : Parcelable

@Parcelize
data class LoginData(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("company_name")
	val companyName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: Long? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("token")
	val token: String? = null
) : Parcelable

