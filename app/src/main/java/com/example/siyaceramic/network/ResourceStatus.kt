package com.example.siyaceramic.network

/**
 * Used for handle different response type from server response.
 * myResp used for get and set success response data model.
 * UserTy used for check user api for which type user check Email / Phone.
 * Throwable used for get and set error from network.
 * isLoaderEnable used for check want to show progress or not on API Response handle.
 * ResType Error used for check which API response have error.
 * apiName Used for handle response using api name
 */
sealed class ResourceStatus<out T> {
    class Success<out T>(
            val myResp: T,
            val apiName: String,
            var userTy: String? = "",
            val pos: Int? = 0
    ) : ResourceStatus<T>()

    class Error<out T>(val throwable: T, val apiName: String, val pos: Int? = 0) :
            ResourceStatus<T>()

    class Loading<out Boolean>(
            val isLoaderEnable: Boolean,
            val apiName: String,
            val pos: Int? = 0
    ) : ResourceStatus<kotlin.Boolean>()

    object Remove : ResourceStatus<Nothing>()
}