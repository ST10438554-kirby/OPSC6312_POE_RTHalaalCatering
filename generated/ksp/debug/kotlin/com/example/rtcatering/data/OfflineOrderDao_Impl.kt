package com.example.rtcatering.`data`

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class OfflineOrderDao_Impl(
  __db: RoomDatabase,
) : OfflineOrderDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfOfflineOrderEntity: EntityInsertAdapter<OfflineOrderEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfOfflineOrderEntity = object : EntityInsertAdapter<OfflineOrderEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `offline_orders` (`orderId`,`orderData`,`syncStatus`,`createdAt`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: OfflineOrderEntity) {
        statement.bindText(1, entity.orderId)
        statement.bindText(2, entity.orderData)
        statement.bindText(3, entity.syncStatus)
        statement.bindLong(4, entity.createdAt)
      }
    }
  }

  public override suspend fun insertOrder(order: OfflineOrderEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfOfflineOrderEntity.insert(_connection, order)
  }

  public override suspend fun getPendingOrders(): List<OfflineOrderEntity> {
    val _sql: String = "SELECT * FROM offline_orders WHERE syncStatus = 'PENDING'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfOrderId: Int = getColumnIndexOrThrow(_stmt, "orderId")
        val _columnIndexOfOrderData: Int = getColumnIndexOrThrow(_stmt, "orderData")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<OfflineOrderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: OfflineOrderEntity
          val _tmpOrderId: String
          _tmpOrderId = _stmt.getText(_columnIndexOfOrderId)
          val _tmpOrderData: String
          _tmpOrderData = _stmt.getText(_columnIndexOfOrderData)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = OfflineOrderEntity(_tmpOrderId,_tmpOrderData,_tmpSyncStatus,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllOrders(): List<OfflineOrderEntity> {
    val _sql: String = "SELECT * FROM offline_orders ORDER BY createdAt DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfOrderId: Int = getColumnIndexOrThrow(_stmt, "orderId")
        val _columnIndexOfOrderData: Int = getColumnIndexOrThrow(_stmt, "orderData")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<OfflineOrderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: OfflineOrderEntity
          val _tmpOrderId: String
          _tmpOrderId = _stmt.getText(_columnIndexOfOrderId)
          val _tmpOrderData: String
          _tmpOrderData = _stmt.getText(_columnIndexOfOrderData)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = OfflineOrderEntity(_tmpOrderId,_tmpOrderData,_tmpSyncStatus,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun markAsSynced(orderId: String) {
    val _sql: String = "UPDATE offline_orders SET syncStatus = 'SYNCED' WHERE orderId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, orderId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteOrder(orderId: String) {
    val _sql: String = "DELETE FROM offline_orders WHERE orderId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, orderId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
