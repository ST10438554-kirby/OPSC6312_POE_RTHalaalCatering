
package com.example.rtcatering.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class OfflineSyncManager(context: Context) {

    private val appContext = context.applicationContext

    private val repository =
        OfflineOrderRepository(appContext)

    private val firestoreManager = FirestoreManager()

    private val gson = Gson()

    companion object {
        private const val TAG = "OfflineSyncManager"
    }

    // Check whether the device has internet access.
    fun isOnline(): Boolean {

        val connectivityManager =
            appContext.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

        val network =
            connectivityManager.activeNetwork ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
                ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        ) && capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        )
    }

    // Synchronise locally stored pending orders with Firestore.
    fun syncPendingOrders(
        onComplete: (() -> Unit)? = null
    ) {

        if (!isOnline()) {
            Log.d(TAG, "Device offline. Orders remain pending.")
            onComplete?.invoke()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val pendingOrders =
                    repository.getPendingOrders()

                Log.d(
                    TAG,
                    "Pending orders found: ${pendingOrders.size}"
                )

                if (pendingOrders.isEmpty()) {
                    onComplete?.invoke()
                    return@launch
                }

                val remaining =
                    AtomicInteger(pendingOrders.size)

                pendingOrders.forEach { offlineOrder ->

                    try {

                        val type =
                            object : TypeToken<Map<String, Any>>() {}.type

                        val orderData: Map<String, Any> =
                            gson.fromJson(
                                offlineOrder.orderData,
                                type
                            )

                        // Use the original order ID to prevent duplicates.
                        firestoreManager.saveOrderWithId(
                            documentId = offlineOrder.orderId,
                            orderData = orderData,

                            onSuccess = { documentId ->

                                CoroutineScope(Dispatchers.IO).launch {

                                    try {

                                        repository.markAsSynced(
                                            offlineOrder.orderId
                                        )

                                        Log.d(
                                            TAG,
                                            "Order successfully synced: $documentId"
                                        )

                                    } catch (exception: Exception) {

                                        Log.e(
                                            TAG,
                                            "Could not update local sync status",
                                            exception
                                        )

                                    } finally {

                                        if (remaining.decrementAndGet() == 0) {
                                            onComplete?.invoke()
                                        }
                                    }
                                }
                            },

                            onFailure = { exception ->

                                Log.e(
                                    TAG,
                                    "Failed to sync order: ${offlineOrder.orderId}",
                                    exception
                                )

                                if (remaining.decrementAndGet() == 0) {
                                    onComplete?.invoke()
                                }
                            }
                        )

                    } catch (exception: Exception) {

                        Log.e(
                            TAG,
                            "Could not process offline order",
                            exception
                        )

                        if (remaining.decrementAndGet() == 0) {
                            onComplete?.invoke()
                        }
                    }
                }

            } catch (exception: Exception) {

                Log.e(
                    TAG,
                    "Offline synchronisation failed",
                    exception
                )

                onComplete?.invoke()
            }
        }
    }
}
