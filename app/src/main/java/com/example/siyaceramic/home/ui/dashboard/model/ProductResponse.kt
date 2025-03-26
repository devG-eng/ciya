package com.example.siyaceramic.home.ui.dashboard.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
@Parcelize
data class ProductResponse(

	@field:SerializedName("data")
	val data: ProductData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ProductData(

	@field:SerializedName("pagination")
	val pagination: Pagination? = null,

	@field:SerializedName("data")
	val data: ArrayList<ProItem>? = ArrayList()
) : Parcelable

@Parcelize
data class Pagination(

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
) : Parcelable

@Parcelize
data class ProItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("top_product")
	val topProduct: Int? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("stock")
	val stock: String? = null,

	@field:SerializedName("category")
	val category: Category? = null
) : Parcelable

@Parcelize
data class Category(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
*/
@Parcelize
data class ProductResponse(

	@field:SerializedName("data")
	val data: ArrayList<ProductData>? = ArrayList(),

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ProductData(

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
	val category: ProCategory? = null
) : Parcelable

@Parcelize
data class ProCategory(

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
	val size: String? = ""
) : Parcelable
