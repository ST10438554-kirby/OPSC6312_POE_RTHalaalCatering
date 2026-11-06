package com.example.rtcatering.models

data class Order(
    val orderNumber: String,
    val eventDate: String,
    val guests: Int,
    val orderType: String,
    val deliveryAddress: String,
    val notes: String,
    val total: Double,
    val amountPaid: Double,
    val balance: Double,
    val paymentType: String
)