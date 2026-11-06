
package com.example.rtcatering

import android.os.Bundle
import android.view.Gravity
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rtcatering.data.FirestoreManager
import java.util.Locale

class MyOrdersActivity : AppCompatActivity() {

    private lateinit var ordersContainer: LinearLayout

    private val firestoreManager = FirestoreManager()

    companion object {
        private const val TAG = "MyOrdersActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_orders)

        ordersContainer = findViewById(R.id.ordersContainer)

        val tvBack = findViewById<TextView>(R.id.tvBack)

        tvBack.setOnClickListener {
            finish()
        }

        // Retrieve saved orders from Firebase
        displayOrders()
    }

    private fun displayOrders() {

        ordersContainer.removeAllViews()

        // Display loading message while retrieving orders
        val loadingText = TextView(this).apply {
            text = "Loading your orders..."
            textSize = 16f
            gravity = Gravity.CENTER
            setPadding(20, 80, 20, 20)
            setTextColor(getColor(R.color.rt_text_primary))
        }

        ordersContainer.addView(loadingText)

        firestoreManager.getOrders(

            onSuccess = { orders ->

                Log.d(TAG, "Successfully retrieved ${orders.size} orders")

                ordersContainer.removeAllViews()

                if (orders.isEmpty()) {
                    displayEmptyMessage()
                    return@getOrders
                }

                // Display the newest orders first
                for (order in orders) {
                    displayOrderCard(order)
                }
            },

            onFailure = { exception ->

                Log.e(TAG, "Failed to retrieve orders", exception)

                ordersContainer.removeAllViews()

                val errorText = TextView(this).apply {
                    text = "Unable to load your orders. Please check your internet connection and try again."
                    textSize = 16f
                    gravity = Gravity.CENTER
                    setPadding(20, 80, 20, 20)
                    setTextColor(getColor(R.color.rt_text_primary))
                }

                ordersContainer.addView(errorText)

                Toast.makeText(
                    this,
                    "Could not retrieve orders.",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

    // Display a message when the customer has no orders
    private fun displayEmptyMessage() {

        val emptyText = TextView(this).apply {
            text = "You have no orders yet.\n\nPlace your first catering order to see it here."
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(20, 80, 20, 20)
            setTextColor(getColor(R.color.rt_text_primary))
        }

        ordersContainer.addView(emptyText)
    }

    // Create a card for each order retrieved from Firestore
    private fun displayOrderCard(order: Map<String, Any>) {

        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            setBackgroundResource(R.drawable.card_background)
        }

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(0, 0, 0, 20)
        card.layoutParams = params

        val orderNumber = order["orderNumber"]?.toString() ?: "Unknown"

        val title = TextView(this).apply {
            text = "Order $orderNumber"
            textSize = 20f
            setTextColor(getColor(R.color.rt_primary))
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val eventDate = order["eventDate"]?.toString() ?: "Not specified"
        val guests = (order["guests"] as? Number)?.toInt() ?: 0
        val orderType = order["orderType"]?.toString() ?: "Not specified"
        val paymentType = order["paymentType"]?.toString() ?: "Not specified"
        val status = order["status"]?.toString() ?: "Pending"

        val total = (order["total"] as? Number)?.toDouble() ?: 0.0
        val amountPaid = (order["amountPaid"] as? Number)?.toDouble() ?: 0.0
        val balance = (order["balance"] as? Number)?.toDouble() ?: 0.0

        val details = TextView(this).apply {

            text = String.format(
                Locale.getDefault(),
                "\nEvent Date: %s\n" +
                        "Guests: %d\n" +
                        "Order Type: %s\n" +
                        "Payment: %s\n" +
                        "Total: R %.2f\n" +
                        "Paid: R %.2f\n" +
                        "Balance: R %.2f\n" +
                        "Status: %s",
                eventDate,
                guests,
                orderType,
                paymentType,
                total,
                amountPaid,
                balance,
                status
            )

            textSize = 16f
            setTextColor(getColor(R.color.rt_text_primary))
        }

        card.addView(title)
        card.addView(details)

        ordersContainer.addView(card)
    }
}
