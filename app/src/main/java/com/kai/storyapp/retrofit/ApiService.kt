package com.kai.storyapp.retrofit

import com.kai.storyapp.model.request.LoginRequest
import com.kai.storyapp.model.request.RegisterRequest
import com.kai.storyapp.model.response.LoginResponse
import com.kai.storyapp.model.response.RegisterResponse
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
}