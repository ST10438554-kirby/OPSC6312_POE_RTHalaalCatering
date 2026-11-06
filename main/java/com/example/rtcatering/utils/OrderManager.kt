
package com.example.rtcatering.utils

import com.example.rtcatering.models.Order

object OrderManager {

    private val orders = mutableListOf<Order>()

    // Add a new order.
    fun addOrder(order: Order) {
        orders.add(order)
    }

    // Return a copy of the saved orders.
    fun getOrders(): List<Order> {
        return orders.toList()
    }

    // Find an order using its order number.
    fun getOrder(orderNumber: String): Order? {
        return orders.find {
            it.orderNumber == orderNumber
        }
    }

    // Check whether any orders exist.
    fun hasOrders(): Boolean {
        return orders.isNotEmpty()
    }

    // Clear orders if required.
    fun clearOrders() {
        orders.clear()
    }
}
