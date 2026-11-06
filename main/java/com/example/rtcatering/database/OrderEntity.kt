package com.example.rtcatering.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val orderNumber: String,
    val eventDate: String,
    val guests: Int,
    val orderType: String,
    val deliveryAddress: String,
    val notes: String,
    val total: Double,
    val amountPaid: Double,
    val balance: Double,
    val paymentType: String,

    // Tracks whether this order has been uploaded to Firebase.
    val isSynced: Boolean = false
)