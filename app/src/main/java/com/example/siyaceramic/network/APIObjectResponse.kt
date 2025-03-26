package com.example.siyaceramic.network

import com.google.gson.annotations.SerializedName

/**
 * Common class for Handle API response.
 * data , message and taken common res from all api.
 */
data class APIObjectResponse<T>(

    @field:SerializedName("data")
    val data: Int = -1,

    @field:SerializedName("message")
    var message: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("API")
    var api: String? = ""
)