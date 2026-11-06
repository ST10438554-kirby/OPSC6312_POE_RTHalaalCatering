
package com.example.rtcatering.models

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val minimumQuantity: Int,
    val imageResId: Int = 0
)
