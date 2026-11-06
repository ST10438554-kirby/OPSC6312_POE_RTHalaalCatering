package com.example.rtcatering.`data`

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
  private val _offlineOrderDao: Lazy<OfflineOrderDao> = lazy {
    OfflineOrderDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "89281e99fc3d706414d4f3693845091a", "39b4db0950184364e8512a0819b56e22") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `offline_orders` (`orderId` TEXT NOT NULL, `orderData` TEXT NOT NULL, `syncStatus` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`orderId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '89281e99fc3d706414d4f3693845091a')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `offline_orders`")
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
        val _columnsOfflineOrders: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsOfflineOrders.put("orderId", TableInfo.Column("orderId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOfflineOrders.put("orderData", TableInfo.Column("orderData", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOfflineOrders.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOfflineOrders.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysOfflineOrders: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesOfflineOrders: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoOfflineOrders: TableInfo = TableInfo("offline_orders", _columnsOfflineOrders, _foreignKeysOfflineOrders, _indicesOfflineOrders)
        val _existingOfflineOrders: TableInfo = read(connection, "offline_orders")
        if (!_infoOfflineOrders.equals(_existingOfflineOrders)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |offline_orders(com.example.rtcatering.data.OfflineOrderEntity).
              | Expected:
              |""".trimMargin() + _infoOfflineOrders + """
              |
              | Found:
              |""".trimMargin() + _existingOfflineOrders)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "offline_orders")
  }

  public override fun clearAllTables() {
    super.performClear(false, "offline_orders")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(OfflineOrderDao::class, OfflineOrderDao_Impl.getRequiredConverters())
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

  public override fun offlineOrderDao(): OfflineOrderDao = _offlineOrderDao.value
}
