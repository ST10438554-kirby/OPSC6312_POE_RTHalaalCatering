
package com.example.rtcatering.utils

import com.example.rtcatering.models.CartItem
import com.example.rtcatering.models.Product

object CartManager {

    val items = mutableListOf<CartItem>()

    // Add a product, enforcing its minimum quantity.
    fun addItem(product: Product, quantity: Int) {

        val validQuantity = quantity.coerceAtLeast(product.minimumQuantity)

        val existingItem = items.find {
            it.product.id == product.id
        }

        if (existingItem != null) {
            existingItem.quantity += validQuantity
        } else {
            items.add(
                CartItem(
                    product = product,
                    quantity = validQuantity
                )
            )
        }
    }

    // Update an existing product quantity.
    fun updateQuantity(productId: Int, quantity: Int) {

        val item = items.find {
            it.product.id == productId
        }

        if (item != null) {
            item.quantity = quantity.coerceAtLeast(
                item.product.minimumQuantity
            )
        }
    }

    // Remove a product.
    fun removeItem(productId: Int) {
        items.removeAll {
            it.product.id == productId
        }
    }

    // Calculate the cart total.
    fun getTotal(): Double {
        return items.sumOf {
            it.getSubtotal()
        }
    }

    // Check if the cart is empty.
    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    // Count total units.
    fun getTotalQuantity(): Int {
        return items.sumOf {
            it.quantity
        }
    }

    // Clear cart after successful order placement.
    fun clearCart() {
        items.clear()
    }
}
