package com.example.rtcatering.database

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class OrderDao_Impl(
  __db: RoomDatabase,
) : OrderDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfOrderEntity: EntityInsertAdapter<OrderEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfOrderEntity = object : EntityInsertAdapter<OrderEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `orders` (`id`,`orderNumber`,`eventDate`,`guests`,`orderType`,`deliveryAddress`,`notes`,`total`,`amountPaid`,`balance`,`paymentType`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: OrderEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.orderNumber)
        statement.bindText(3, entity.eventDate)
        statement.bindLong(4, entity.guests.toLong())
        statement.bindText(5, entity.orderType)
        statement.bindText(6, entity.deliveryAddress)
        statement.bindText(7, entity.notes)
        statement.bindDouble(8, entity.total)
        statement.bindDouble(9, entity.amountPaid)
        statement.bindDouble(10, entity.balance)
        statement.bindText(11, entity.paymentType)
        val _tmp: Int = if (entity.isSynced) 1 else 0
        statement.bindLong(12, _tmp.toLong())
      }
    }
  }

  public override suspend fun insertOrder(order: OrderEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfOrderEntity.insert(_connection, order)
  }

  public override suspend fun getAllOrders(): List<OrderEntity> {
    val _sql: String = "SELECT * FROM orders ORDER BY id DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfOrderNumber: Int = getColumnIndexOrThrow(_stmt, "orderNumber")
        val _columnIndexOfEventDate: Int = getColumnIndexOrThrow(_stmt, "eventDate")
        val _columnIndexOfGuests: Int = getColumnIndexOrThrow(_stmt, "guests")
        val _columnIndexOfOrderType: Int = getColumnIndexOrThrow(_stmt, "orderType")
        val _columnIndexOfDeliveryAddress: Int = getColumnIndexOrThrow(_stmt, "deliveryAddress")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfTotal: Int = getColumnIndexOrThrow(_stmt, "total")
        val _columnIndexOfAmountPaid: Int = getColumnIndexOrThrow(_stmt, "amountPaid")
        val _columnIndexOfBalance: Int = getColumnIndexOrThrow(_stmt, "balance")
        val _columnIndexOfPaymentType: Int = getColumnIndexOrThrow(_stmt, "paymentType")
        val _columnIndexOfIsSynced: Int = getColumnIndexOrThrow(_stmt, "isSynced")
        val _result: MutableList<OrderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: OrderEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpOrderNumber: String
          _tmpOrderNumber = _stmt.getText(_columnIndexOfOrderNumber)
          val _tmpEventDate: String
          _tmpEventDate = _stmt.getText(_columnIndexOfEventDate)
          val _tmpGuests: Int
          _tmpGuests = _stmt.getLong(_columnIndexOfGuests).toInt()
          val _tmpOrderType: String
          _tmpOrderType = _stmt.getText(_columnIndexOfOrderType)
          val _tmpDeliveryAddress: String
          _tmpDeliveryAddress = _stmt.getText(_columnIndexOfDeliveryAddress)
          val _tmpNotes: String
          _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          val _tmpTotal: Double
          _tmpTotal = _stmt.getDouble(_columnIndexOfTotal)
          val _tmpAmountPaid: Double
          _tmpAmountPaid = _stmt.getDouble(_columnIndexOfAmountPaid)
          val _tmpBalance: Double
          _tmpBalance = _stmt.getDouble(_columnIndexOfBalance)
          val _tmpPaymentType: String
          _tmpPaymentType = _stmt.getText(_columnIndexOfPaymentType)
          val _tmpIsSynced: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSynced).toInt()
          _tmpIsSynced = _tmp != 0
          _item = OrderEntity(_tmpId,_tmpOrderNumber,_tmpEventDate,_tmpGuests,_tmpOrderType,_tmpDeliveryAddress,_tmpNotes,_tmpTotal,_tmpAmountPaid,_tmpBalance,_tmpPaymentType,_tmpIsSynced)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getUnsyncedOrders(): List<OrderEntity> {
    val _sql: String = "SELECT * FROM orders WHERE isSynced = 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfOrderNumber: Int = getColumnIndexOrThrow(_stmt, "orderNumber")
        val _columnIndexOfEventDate: Int = getColumnIndexOrThrow(_stmt, "eventDate")
        val _columnIndexOfGuests: Int = getColumnIndexOrThrow(_stmt, "guests")
        val _columnIndexOfOrderType: Int = getColumnIndexOrThrow(_stmt, "orderType")
        val _columnIndexOfDeliveryAddress: Int = getColumnIndexOrThrow(_stmt, "deliveryAddress")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfTotal: Int = getColumnIndexOrThrow(_stmt, "total")
        val _columnIndexOfAmountPaid: Int = getColumnIndexOrThrow(_stmt, "amountPaid")
        val _columnIndexOfBalance: Int = getColumnIndexOrThrow(_stmt, "balance")
        val _columnIndexOfPaymentType: Int = getColumnIndexOrThrow(_stmt, "paymentType")
        val _columnIndexOfIsSynced: Int = getColumnIndexOrThrow(_stmt, "isSynced")
        val _result: MutableList<OrderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: OrderEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpOrderNumber: String
          _tmpOrderNumber = _stmt.getText(_columnIndexOfOrderNumber)
          val _tmpEventDate: String
          _tmpEventDate = _stmt.getText(_columnIndexOfEventDate)
          val _tmpGuests: Int
          _tmpGuests = _stmt.getLong(_columnIndexOfGuests).toInt()
          val _tmpOrderType: String
          _tmpOrderType = _stmt.getText(_columnIndexOfOrderType)
          val _tmpDeliveryAddress: String
          _tmpDeliveryAddress = _stmt.getText(_columnIndexOfDeliveryAddress)
          val _tmpNotes: String
          _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          val _tmpTotal: Double
          _tmpTotal = _stmt.getDouble(_columnIndexOfTotal)
          val _tmpAmountPaid: Double
          _tmpAmountPaid = _stmt.getDouble(_columnIndexOfAmountPaid)
          val _tmpBalance: Double
          _tmpBalance = _stmt.getDouble(_columnIndexOfBalance)
          val _tmpPaymentType: String
          _tmpPaymentType = _stmt.getText(_columnIndexOfPaymentType)
          val _tmpIsSynced: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSynced).toInt()
          _tmpIsSynced = _tmp != 0
          _item = OrderEntity(_tmpId,_tmpOrderNumber,_tmpEventDate,_tmpGuests,_tmpOrderType,_tmpDeliveryAddress,_tmpNotes,_tmpTotal,_tmpAmountPaid,_tmpBalance,_tmpPaymentType,_tmpIsSynced)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun markOrderAsSynced(orderId: Int) {
    val _sql: String = "UPDATE orders SET isSynced = 1 WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, orderId.toLong())
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
