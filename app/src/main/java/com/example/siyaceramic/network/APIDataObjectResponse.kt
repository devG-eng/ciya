package com.example.siyaceramic.network

import com.google.gson.annotations.SerializedName

/**
 * Common class for Handle API response.
 * data , message and taken common res from all api.
 */
data class APIDataObjectResponse<T>(
    @field:SerializedName("statuscode")
    val statuscode: Int? = null,

    @field:SerializedName("data")
    val data: T? = null,

    @field:SerializedName("Token")
    val token: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("API")
    var api: String? = ""
)