
package com.example.rtcatering.data

import android.content.Context

class OfflineOrderRepository(context: Context) {

    private val orderDao =
        AppDatabase.getDatabase(context).offlineOrderDao()

    suspend fun saveOfflineOrder(order: OfflineOrderEntity) {
        orderDao.insertOrder(order)
    }

    suspend fun getPendingOrders(): List<OfflineOrderEntity> {
        return orderDao.getPendingOrders()
    }

    suspend fun getAllOrders(): List<OfflineOrderEntity> {
        return orderDao.getAllOrders()
    }

    suspend fun markAsSynced(orderId: String) {
        orderDao.markAsSynced(orderId)
    }

    suspend fun deleteOrder(orderId: String) {
        orderDao.deleteOrder(orderId)
    }
}
