package com.example.rtcatering.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offline_orders")
data class OfflineOrderEntity(
    @PrimaryKey
    val orderId: String,

    val orderData: String,

    val syncStatus: String = "PENDING",

    val createdAt: Long = System.currentTimeMillis()
)