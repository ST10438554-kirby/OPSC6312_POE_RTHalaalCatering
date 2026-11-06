package com.example.rtcatering.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/Auth/register")
    fun register(
        @Body request: RegisterRequest
    ): Call<AuthResponse>

    @POST("api/Auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<AuthResponse>
}