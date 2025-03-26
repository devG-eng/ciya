package com.example.siyaceramic.home.ui.dashboard.model

import android.os.Parcelable
import com.example.siyaceramic.home.ui.home.model.CategoriesItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategorySizeResponse(

	@field:SerializedName("data")
	val data: ArrayList<CategoriesItem>? = ArrayList(),

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
) : Parcelable
