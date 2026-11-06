
package com.example.rtcatering.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * Manages online catering order storage using Firebase Cloud Firestore.
 */
class FirestoreManager {

    private val db = FirebaseFirestore.getInstance()

    companion object {
        private const val TAG = "FirestoreManager"
        private const val ORDERS_COLLECTION = "orders"
    }

    // Save a new order to Firestore.
    fun saveOrder(
        orderData: Map<String, Any>,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection(ORDERS_COLLECTION)
            .add(orderData)
            .addOnSuccessListener { document ->
                Log.d(TAG, "Order saved: ${document.id}")
                onSuccess(document.id)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to save order", exception)
                onFailure(exception)
            }
    }

    // Retrieve orders from Firestore.
    fun getOrders(
        onSuccess: (List<Map<String, Any>>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection(ORDERS_COLLECTION)
            .orderBy("eventDate", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->

                val orders = result.documents.mapNotNull { document ->
                    document.data?.toMutableMap()?.apply {
                        put("documentId", document.id)
                    }
                }

                Log.d(TAG, "Retrieved ${orders.size} orders")
                onSuccess(orders)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to retrieve orders", exception)
                onFailure(exception)
            }
    }

    // Save an offline order using a fixed document ID.
    // This prevents duplicate Firestore documents during retries.
    fun saveOrderWithId(
        documentId: String,
        orderData: Map<String, Any>,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection(ORDERS_COLLECTION)
            .document(documentId)
            .set(orderData)
            .addOnSuccessListener {
                Log.d(TAG, "Order saved or updated: $documentId")
                onSuccess(documentId)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to sync order: $documentId", exception)
                onFailure(exception)
            }
    }
}
