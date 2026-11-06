
package com.example.rtcatering.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OfflineOrderDao {

    // Save an order locally
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OfflineOrderEntity)

    // Retrieve all orders waiting to sync
    @Query("SELECT * FROM offline_orders WHERE syncStatus = 'PENDING'")
    suspend fun getPendingOrders(): List<OfflineOrderEntity>

    // Retrieve all locally stored orders
    @Query("SELECT * FROM offline_orders ORDER BY createdAt DESC")
    suspend fun getAllOrders(): List<OfflineOrderEntity>

    // Mark an order as successfully synced
    @Query("UPDATE offline_orders SET syncStatus = 'SYNCED' WHERE orderId = :orderId")
    suspend fun markAsSynced(orderId: String)

    // Delete an order from local storage
    @Query("DELETE FROM offline_orders WHERE orderId = :orderId")
    suspend fun deleteOrder(orderId: String)
}
