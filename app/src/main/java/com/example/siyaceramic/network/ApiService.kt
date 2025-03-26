package com.example.siyaceramic.network

import com.example.siyaceramic.home.ui.dashboard.model.CategoryResponse
import com.example.siyaceramic.home.ui.dashboard.model.CategorySizeResponse
import com.example.siyaceramic.home.ui.dashboard.model.ProductResponse
import com.example.siyaceramic.home.ui.home.model.HomeResponse
import com.example.siyaceramic.home.ui.notifications.model.LogoutResponse
import com.example.siyaceramic.home.ui.notifications.model.ProfileResponse
import com.example.siyaceramic.login.model.LoginReqModel
import com.example.siyaceramic.login.model.LoginResponse
import com.example.siyaceramic.register.model.RegisterReqModel
import com.example.siyaceramic.register.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST("public/api/{api}")
    fun registerUser(@Path("api") apiName: String, @Body registerReqModel: RegisterReqModel): Call<RegisterResponse>

    @POST("public/api/{api}")
    fun loginUser(@Path("api") apiName: String, @Body loginReqModel: LoginReqModel): Call<LoginResponse>

    @GET("public/api/{api}")
    fun getHomeData(@Path("api") apiName: String): Call<HomeResponse>

    @GET("public/api/{api}")
    fun getCategoriesData(@Path("api") apiName: String): Call<CategoryResponse>

    @GET("public/api/{api}")
    fun getProductsData(@Path("api") apiName: String, @Query("search") search: String): Call<ProductResponse>

    @GET("public/api/{api}")
    fun getCatData(@Path("api") apiName: String, @Query("search") search: String): Call<CategoryResponse>

    @GET("public/api/{api}")
    fun getProductsDetail(@Path("api") apiName: String, @Query("size_id") size_id: Int, @Query("category_id") category_id: Int): Call<ProductResponse>

    @GET("public/api/{api}")
    fun getProductDetail(@Path("api") apiName: String,  @Query("category_id") category_id: Int): Call<ProductResponse>

    @GET("public/api/get-category-by-size/{size}")
    fun getCatSizeData(@Path("size") size: Int): Call<CategorySizeResponse>

    @GET("public/api/{api}")
    fun getProfileData(@Path("api") apiName: String): Call<ProfileResponse>

    @POST("public/api/{api}")
    fun logoutUser(@Path("api") apiName: String): Call<LogoutResponse>



    /*  @Multipart
      @POST("v1/Master/{api}")
      fun uploadDocument(
          @Path("api") apiName: String,
          @Part file: MultipartBody.Part
      ): Call<UploadDocResponse>*/
    }