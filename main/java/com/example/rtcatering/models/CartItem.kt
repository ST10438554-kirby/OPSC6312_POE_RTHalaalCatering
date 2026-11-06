package com.example.rtcatering.models

data class CartItem(
    val product: Product,
    var quantity: Int
) {
    fun getSubtotal(): Double {
        return product.price * quantity
    }
}