package com.example.rtcatering.api

data class AuthResponse(
    val message: String,
    val userId: Int? = null,
    val fullName: String? = null,
    val email: String? = null,
    val phone: String? = null
)