package com.example.rtcatering

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_order_confirmation)

        val tvOrderNumber =
            findViewById<TextView>(R.id.tvOrderNumber)

        val tvOrderDetails =
            findViewById<TextView>(R.id.tvOrderDetails)

        val tvPaymentDetails =
            findViewById<TextView>(R.id.tvPaymentDetails)

        val btnBackHome =
            findViewById<Button>(R.id.btnBackHome)

        val btnViewOrders =
            findViewById<Button>(R.id.btnViewOrders)


        val orderNumber =
            intent.getStringExtra("orderNumber")
                ?: "RT-10001"

        val eventDate =
            intent.getStringExtra("eventDate")
                ?: ""

        val guests =
            intent.getIntExtra("guests", 0)

        val orderType =
            intent.getStringExtra("orderType")
                ?: ""

        val deliveryAddress =
            intent.getStringExtra("deliveryAddress")
                ?: ""

        val notes =
            intent.getStringExtra("notes")
                ?: ""

        val total =
            intent.getDoubleExtra("total", 0.0)

        val amountPaid =
            intent.getDoubleExtra("amountPaid", 0.0)

        val balance =
            intent.getDoubleExtra("balance", 0.0)

        val paymentType =
            intent.getStringExtra("paymentType")
                ?: ""


        tvOrderNumber.text =
            "Order Reference: $orderNumber"


        var orderDetailsText =
            "EVENT DATE\n$eventDate\n\n" +
                    "NUMBER OF GUESTS\n$guests\n\n" +
                    "ORDER TYPE\n$orderType"


        if (orderType == "Delivery") {

            orderDetailsText +=
                "\n\nDELIVERY ADDRESS\n$deliveryAddress"
        }


        if (notes.isNotEmpty()) {

            orderDetailsText +=
                "\n\nADDITIONAL NOTES\n$notes"
        }


        tvOrderDetails.text =
            orderDetailsText


        tvPaymentDetails.text =
            String.format(
                "PAYMENT\n\n" +
                        "Payment option: %s\n\n" +
                        "Order total: R %.2f\n" +
                        "Amount paid: R %.2f\n" +
                        "Balance: R %.2f",
                paymentType,
                total,
                amountPaid,
                balance
            )


        btnBackHome.setOnClickListener {

            val intent =
                Intent(
                    this,
                    HomeActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP

            startActivity(intent)

            finish()
        }


        btnViewOrders.setOnClickListener {

            val intent =
                Intent(
                    this,
                    MyOrdersActivity::class.java
                )

            startActivity(intent)

            finish()

        }
    }
}