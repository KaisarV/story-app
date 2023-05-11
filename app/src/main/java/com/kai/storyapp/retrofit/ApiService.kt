package com.kai.storyapp.retrofit

import com.kai.storyapp.model.request.LoginRequest
import com.kai.storyapp.model.request.RegisterRequest
import com.kai.storyapp.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/v1/register")
    fun registerUser(
        @Body request: RegisterRequest,
    ): Call<RegisterResponse>

    @POST("/v1/login")
    fun loginUser(
        @Body request: LoginRequest,
    ): Call<LoginResponse>

    @GET("/v1/stories")
    fun stories(
        @Header("Authorization") token: String
    ): Call<StoryResponse>

    @Multipart
    @POST("/v1/stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ErrorResponse>

    @GET("/v1/stories")
    fun storyLocation(
        @Header("Authorization") token: String,
        @Query("location") queryParam: String
    ): Call<LocationResponse>
}