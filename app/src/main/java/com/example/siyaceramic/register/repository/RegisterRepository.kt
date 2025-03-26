package com.example.siyaceramic.register.repository

import android.util.Log
import com.example.siyaceramic.network.ApiService
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.register.model.RegisterReqModel
import org.json.JSONObject
import retrofit2.awaitResponse

class RegisterRepository(private val apiService: ApiService) {
    /**
     * Register Api */


    suspend fun registerUser(registerReqModel: RegisterReqModel, apiName: String): ResourceStatus<*> {

        kotlin.runCatching {
            apiService.registerUser(apiName, registerReqModel).awaitResponse()
        }.onSuccess {
            Log.d("CODE","it.code()"+it.code())
            return if (it.code() == 200) {

                when (it.body()?.status) {
                    false -> {
                        return ResourceStatus.Error(Throwable(it.body()?.message), apiName)
                    }
                    else -> {
                        Log.d("CODE", "it.body()" + it.body())
                        ResourceStatus.Success(it.body(), apiName)
                    }
                }

            }

            else {
                kotlin.runCatching {
                    it.errorBody()?.string() ?: "{}"
                }.onSuccess { error ->
                    return if (it.code() == 401) {
                        ResourceStatus.Error(Throwable("Unwanted authorization token passed"), apiName)
                    }else {
                        Log.d("CODE", "error" + error)
                        val jObjError = JSONObject(error)
                        ResourceStatus.Error(
                            Throwable(jObjError.getString("Message")),
                            apiName
                        )
                    }
                }.onFailure {
                    return ResourceStatus.Error(Throwable("Something Went Wrong"), apiName)
                }
                return@onSuccess
            }
        }.onFailure {
            return ResourceStatus.Error(it, apiName)
        }
        return ResourceStatus.Loading(false, apiName)

    }

}