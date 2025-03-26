package com.example.siyaceramic.home.ui.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
@Parcelize
data class HomeResponse(

	@field:SerializedName("data")
	val data: Home? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Home(

	@field:SerializedName("categories")
	val categories: ArrayList<CategoriesItem>? = ArrayList(),

	@field:SerializedName("best_product")
	val bestProduct: ArrayList<BestProductItem>? = ArrayList()
) : Parcelable

@Parcelize
data class BestProductItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("top_product")
	val topProduct: Int? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("stock")
	val stock: String? = null
) : Parcelable

@Parcelize
data class CategoriesItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("width")
	val width: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("height")
	val height: String? = null
) : Parcelable
*/
@Parcelize
data class HomeResponse(

	@field:SerializedName("data")
	val data: HomeData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CategoriesItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
) : Parcelable

@Parcelize
data class SizesItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("size")
	val size: String? = null
) : Parcelable

@Parcelize
data class BestProductItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("top_product")
	val topProduct: Int? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("product_size")
	val productSizes: ProductSizesItem? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("stock")
	val stock: String? = null,

	@field:SerializedName("category")
	val category: Categories? = null,

	@field:SerializedName("pieces_per_box")
	val piecesPerBox: String? = null
) : Parcelable
@Parcelize
data class Categories(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
) : Parcelable

@Parcelize
data class ProductSizesItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("size")
	val size: String? = null
) : Parcelable

@Parcelize
data class HomeData(

	@field:SerializedName("sizes")
	val sizes: ArrayList<SizesItem>? = ArrayList(),

	@field:SerializedName("categories")
	val categories: ArrayList<CategoriesItem>? = ArrayList(),

	@field:SerializedName("best_product")
	val bestProduct: ArrayList<BestProductItem>? = ArrayList(),

	@field:SerializedName("user")
	val user: UserProfile? = null

) : Parcelable

@Parcelize
data class UserProfile(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("company_name")
	val companyName: String? = "",

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: Long? = null,

	@field:SerializedName("email")
	val email: String? = "",

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
