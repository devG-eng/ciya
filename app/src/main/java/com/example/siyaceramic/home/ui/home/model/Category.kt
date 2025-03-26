package com.example.siyaceramic.home.ui.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(var catName : String = "",var catQty: String = "",var catImage : String = "",var catSize: String = "",var catCat: String = ""): Parcelable
