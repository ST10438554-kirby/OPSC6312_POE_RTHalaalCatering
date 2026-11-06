
package com.example.rtcatering

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import com.example.rtcatering.utils.CartManager
import com.example.rtcatering.models.Order
import com.example.rtcatering.utils.OrderManager
import com.example.rtcatering.data.FirestoreManager
import com.example.rtcatering.data.OfflineOrderEntity
import com.example.rtcatering.data.OfflineOrderRepository
import com.example.rtcatering.data.OfflineSyncManager

import com.google.gson.Gson

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    private lateinit var etEventDate: EditText
    private lateinit var etGuests: EditText
    private lateinit var etDeliveryAddress: EditText
    private lateinit var etNotes: EditText

    private lateinit var rgOrderType: RadioGroup
    private lateinit var rbCollection: RadioButton
    private lateinit var rbDelivery: RadioButton

    private lateinit var rgPayment: RadioGroup
    private lateinit var rbFullPayment: RadioButton
    private lateinit var rbDeposit: RadioButton

    private lateinit var tvDeliveryAddressLabel: TextView
    private lateinit var tvPaymentSummary: TextView
    private lateinit var orderSummaryContainer: LinearLayout

    private var selectedDate = ""

    private val firestoreManager = FirestoreManager()

    companion object {
        private const val TAG = "CheckoutActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_checkout)

        etEventDate = findViewById(R.id.etEventDate)
        etGuests = findViewById(R.id.etGuests)
        etDeliveryAddress = findViewById(R.id.etDeliveryAddress)
        etNotes = findViewById(R.id.etNotes)

        rgOrderType = findViewById(R.id.rgOrderType)
        rbCollection = findViewById(R.id.rbCollection)
        rbDelivery = findViewById(R.id.rbDelivery)

        rgPayment = findViewById(R.id.rgPayment)
        rbFullPayment = findViewById(R.id.rbFullPayment)
        rbDeposit = findViewById(R.id.rbDeposit)

        tvDeliveryAddressLabel =
            findViewById(R.id.tvDeliveryAddressLabel)

        tvPaymentSummary =
            findViewById(R.id.tvPaymentSummary)

        orderSummaryContainer =
            findViewById(R.id.orderSummaryContainer)

        val tvBack = findViewById<TextView>(R.id.tvBack)
        val btnPlaceOrder =
            findViewById<Button>(R.id.btnPlaceOrder)

        // Default selections
        rbCollection.isChecked = true
        rbFullPayment.isChecked = true

        tvDeliveryAddressLabel.visibility = View.GONE
        etDeliveryAddress.visibility = View.GONE

        tvBack.setOnClickListener {
            finish()
        }

        etEventDate.setOnClickListener {
            showDatePicker()
        }

        // Collection / Delivery
        rgOrderType.setOnCheckedChangeListener { _, checkedId ->

            if (checkedId == R.id.rbDelivery) {

                tvDeliveryAddressLabel.visibility = View.VISIBLE
                etDeliveryAddress.visibility = View.VISIBLE

            } else {

                tvDeliveryAddressLabel.visibility = View.GONE
                etDeliveryAddress.visibility = View.GONE
            }
        }

        // Payment selection
        rgPayment.setOnCheckedChangeListener { _, _ ->
            updatePaymentSummary()
        }

        displayOrderSummary()
        updatePaymentSummary()

        btnPlaceOrder.setOnClickListener {
            placeOrder()
        }
    }

    private fun showDatePicker() {

        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->

                val selectedCalendar = Calendar.getInstance()

                selectedCalendar.set(
                    selectedYear,
                    selectedMonth,
                    selectedDay
                )

                val dateFormat = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                )

                selectedDate = dateFormat.format(
                    selectedCalendar.time
                )

                etEventDate.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePicker.datePicker.minDate =
            System.currentTimeMillis()

        datePicker.show()
    }

    private fun displayOrderSummary() {

        orderSummaryContainer.removeAllViews()

        for (cartItem in CartManager.items) {

            val productRow = TextView(this)

            productRow.text = String.format(
                Locale.getDefault(),
                "%s  x%d     R %.2f",
                cartItem.product.name,
                cartItem.quantity,
                cartItem.getSubtotal()
            )

            productRow.textSize = 16f

            productRow.setTextColor(
                getColor(R.color.rt_text_primary)
            )

            productRow.setPadding(0, 8, 0, 8)

            orderSummaryContainer.addView(productRow)
        }

        val divider = TextView(this)

        divider.text = "------------------------------"

        divider.setPadding(0, 8, 0, 8)

        orderSummaryContainer.addView(divider)

        val total = TextView(this)

        total.text = String.format(
            Locale.getDefault(),
            "TOTAL: R %.2f",
            CartManager.getTotal()
        )

        total.textSize = 18f

        total.setTextColor(
            getColor(R.color.rt_primary)
        )

        total.setTypeface(
            null,
            android.graphics.Typeface.BOLD
        )

        orderSummaryContainer.addView(total)
    }

    private fun updatePaymentSummary() {

        val total = CartManager.getTotal()

        if (rbDeposit.isChecked) {

            val deposit = total * 0.50
            val balance = total - deposit

            tvPaymentSummary.text = String.format(
                Locale.getDefault(),
                "Total: R %.2f\nDeposit: R %.2f\nBalance: R %.2f",
                total,
                deposit,
                balance
            )

        } else {

            tvPaymentSummary.text = String.format(
                Locale.getDefault(),
                "Total: R %.2f\nAmount payable: R %.2f\nBalance: R 0.00",
                total,
                total
            )
        }
    }

    private fun placeOrder() {

        val guestsText = etGuests.text.toString().trim()
        val notes = etNotes.text.toString().trim()

        // Validate event date
        if (selectedDate.isEmpty()) {

            etEventDate.error = "Please select the event date"

            Toast.makeText(
                this,
                "Please select an event date.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // Validate guests
        if (guestsText.isEmpty()) {

            etGuests.error = "Please enter the number of guests"
            etGuests.requestFocus()

            return
        }

        val guests = guestsText.toIntOrNull()

        if (guests == null || guests <= 0) {

            etGuests.error = "Please enter a valid number of guests"
            etGuests.requestFocus()

            return
        }

        // Validate delivery address
        val deliveryAddress =
            etDeliveryAddress.text.toString().trim()

        if (rbDelivery.isChecked && deliveryAddress.isEmpty()) {

            etDeliveryAddress.error =
                "Please enter the delivery address"

            etDeliveryAddress.requestFocus()

            return
        }

        // Validate cart
        if (CartManager.items.isEmpty()) {

            Toast.makeText(
                this,
                "Your cart is empty.",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        val orderType =
            if (rbDelivery.isChecked) "Delivery" else "Collection"

        val paymentType =
            if (rbDeposit.isChecked) "50% Deposit" else "Full Payment"

        val total = CartManager.getTotal()

        val amountPaid =
            if (rbDeposit.isChecked) total * 0.50 else total

        val balance = total - amountPaid

        val orderNumber =
            "RT-" + System.currentTimeMillis().toString().takeLast(6)

        // Create order
        val order = Order(
            orderNumber = orderNumber,
            eventDate = selectedDate,
            guests = guests,
            orderType = orderType,
            deliveryAddress = deliveryAddress,
            notes = notes,
            total = total,
            amountPaid = amountPaid,
            balance = balance,
            paymentType = paymentType
        )

        // Prepare Firestore data
        val orderData = hashMapOf<String, Any>(
            "orderNumber" to orderNumber,
            "eventDate" to selectedDate,
            "guests" to guests,
            "orderType" to orderType,
            "deliveryAddress" to deliveryAddress,
            "notes" to notes,
            "total" to total,
            "amountPaid" to amountPaid,
            "balance" to balance,
            "paymentType" to paymentType,
            "status" to "Pending",
            "createdAt" to System.currentTimeMillis(),

            "items" to CartManager.items.map { cartItem ->
                hashMapOf(
                    "productName" to cartItem.product.name,
                    "quantity" to cartItem.quantity,
                    "subtotal" to cartItem.getSubtotal()
                )
            }
        )

        val syncManager = OfflineSyncManager(this)

        // Check internet connection
        if (syncManager.isOnline()) {

            firestoreManager.saveOrderWithId(
                documentId = orderNumber,
                orderData = orderData,

                onSuccess = { documentId ->

                    Log.d(TAG, "Order saved online: $documentId")

                    runOnUiThread {

                        OrderManager.addOrder(order)

                        Toast.makeText(
                            this,
                            "Your order has been saved successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        showOrderConfirmation(
                            orderNumber,
                            guests,
                            orderType,
                            deliveryAddress,
                            notes,
                            total,
                            amountPaid,
                            balance,
                            paymentType
                        )
                    }
                },

                onFailure = { exception ->

                    Log.e(
                        TAG,
                        "Online save failed. Saving offline.",
                        exception
                    )

                    saveOrderOffline(
                        orderNumber,
                        orderData,
                        order,
                        guests,
                        orderType,
                        deliveryAddress,
                        notes,
                        total,
                        amountPaid,
                        balance,
                        paymentType
                    )
                }
            )

        } else {

            // Save locally when offline
            saveOrderOffline(
                orderNumber,
                orderData,
                order,
                guests,
                orderType,
                deliveryAddress,
                notes,
                total,
                amountPaid,
                balance,
                paymentType
            )
        }
    }

    private fun saveOrderOffline(
        orderNumber: String,
        orderData: HashMap<String, Any>,
        order: Order,
        guests: Int,
        orderType: String,
        deliveryAddress: String,
        notes: String,
        total: Double,
        amountPaid: Double,
        balance: Double,
        paymentType: String
    ) {

        val repository = OfflineOrderRepository(this)

        val offlineOrder = OfflineOrderEntity(
            orderId = orderNumber,
            orderData = Gson().toJson(orderData),
            syncStatus = "PENDING"
        )

        CoroutineScope(Dispatchers.IO).launch {

            try {

                repository.saveOfflineOrder(offlineOrder)

                withContext(Dispatchers.Main) {

                    OrderManager.addOrder(order)

                    Toast.makeText(
                        this@CheckoutActivity,
                        "Order saved on your device. It will sync when internet is available.",
                        Toast.LENGTH_LONG
                    ).show()

                    showOrderConfirmation(
                        orderNumber,
                        guests,
                        orderType,
                        deliveryAddress,
                        notes,
                        total,
                        amountPaid,
                        balance,
                        paymentType
                    )
                }

            } catch (exception: Exception) {

                Log.e(TAG, "Could not save order offline", exception)

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@CheckoutActivity,
                        "Order could not be saved. Please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showOrderConfirmation(
        orderNumber: String,
        guests: Int,
        orderType: String,
        deliveryAddress: String,
        notes: String,
        total: Double,
        amountPaid: Double,
        balance: Double,
        paymentType: String
    ) {

        val intent = Intent(
            this,
            OrderConfirmationActivity::class.java
        )

        intent.putExtra("orderNumber", orderNumber)
        intent.putExtra("eventDate", selectedDate)
        intent.putExtra("guests", guests)
        intent.putExtra("orderType", orderType)
        intent.putExtra("deliveryAddress", deliveryAddress)
        intent.putExtra("notes", notes)
        intent.putExtra("total", total)
        intent.putExtra("amountPaid", amountPaid)
        intent.putExtra("balance", balance)
        intent.putExtra("paymentType", paymentType)

        startActivity(intent)

        CartManager.clearCart()

        finish()
    }
}
