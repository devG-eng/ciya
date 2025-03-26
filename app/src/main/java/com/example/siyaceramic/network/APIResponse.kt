package com.example.siyaceramic.network

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

/**
 * Common class for Handle API response.
 * data , message and taken common res from all api.
 */
data class APIResponse<T>(

    @field:SerializedName("data")
    val data: Objects? = null,

    @field:SerializedName("message")
    var message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("API")
    var api: String? = ""
)