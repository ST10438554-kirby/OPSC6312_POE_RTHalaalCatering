package com.example.rtcatering.api

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val phone: String,
    val password: String
)