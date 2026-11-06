package com.example.rtcatering.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _orderDao: Lazy<OrderDao> = lazy {
    OrderDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "a3fc45ddbc42e66e7ed2c1c58163e55c", "eda95d42e1e54110df31d98dfad9452d") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `orders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `orderNumber` TEXT NOT NULL, `eventDate` TEXT NOT NULL, `guests` INTEGER NOT NULL, `orderType` TEXT NOT NULL, `deliveryAddress` TEXT NOT NULL, `notes` TEXT NOT NULL, `total` REAL NOT NULL, `amountPaid` REAL NOT NULL, `balance` REAL NOT NULL, `paymentType` TEXT NOT NULL, `isSynced` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a3fc45ddbc42e66e7ed2c1c58163e55c')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `orders`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsOrders: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsOrders.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("orderNumber", TableInfo.Column("orderNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("eventDate", TableInfo.Column("eventDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("guests", TableInfo.Column("guests", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("orderType", TableInfo.Column("orderType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("deliveryAddress", TableInfo.Column("deliveryAddress", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("notes", TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("total", TableInfo.Column("total", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("amountPaid", TableInfo.Column("amountPaid", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("balance", TableInfo.Column("balance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("paymentType", TableInfo.Column("paymentType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("isSynced", TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysOrders: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesOrders: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoOrders: TableInfo = TableInfo("orders", _columnsOrders, _foreignKeysOrders, _indicesOrders)
        val _existingOrders: TableInfo = read(connection, "orders")
        if (!_infoOrders.equals(_existingOrders)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |orders(com.example.rtcatering.database.OrderEntity).
              | Expected:
              |""".trimMargin() + _infoOrders + """
              |
              | Found:
              |""".trimMargin() + _existingOrders)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "orders")
  }

  public override fun clearAllTables() {
    super.performClear(false, "orders")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(OrderDao::class, OrderDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun orderDao(): OrderDao = _orderDao.value
}
