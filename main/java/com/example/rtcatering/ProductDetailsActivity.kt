
package com.example.rtcatering

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rtcatering.models.Product
import com.example.rtcatering.utils.CartManager

class ProductDetailsActivity : AppCompatActivity() {

    private var productPrice = 0.0
    private var minimumQuantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_details)

        val tvBack = findViewById<TextView>(R.id.tvBack)

        val ivProductImage =
            findViewById<ImageView>(R.id.ivProductImage)

        val tvCategory =
            findViewById<TextView>(R.id.tvCategory)

        val tvProductName =
            findViewById<TextView>(R.id.tvProductName)

        val tvDescription =
            findViewById<TextView>(R.id.tvDescription)

        val tvPrice =
            findViewById<TextView>(R.id.tvPrice)

        val tvMinimum =
            findViewById<TextView>(R.id.tvMinimum)

        val etQuantity =
            findViewById<EditText>(R.id.etQuantity)

        val btnAddToCart =
            findViewById<Button>(R.id.btnAddToCart)

        // Receive product information

        val productId =
            intent.getIntExtra("productId", 0)

        val productName =
            intent.getStringExtra("productName") ?: "Product"

        val description =
            intent.getStringExtra("productDescription") ?: ""

        val category =
            intent.getStringExtra("productCategory") ?: ""

        productPrice =
            intent.getDoubleExtra("productPrice", 0.0)

        minimumQuantity =
            intent.getIntExtra("minimumQuantity", 1)

        val productImage =
            intent.getIntExtra("productImage", 0)

        // Display product image

        if (productImage != 0) {
            ivProductImage.setImageResource(productImage)
        }

        // Display product information

        tvProductName.text = productName
        tvDescription.text = description
        tvCategory.text = category

        tvPrice.text = String.format(
            "R %.2f",
            productPrice
        )

        tvMinimum.text =
            "Minimum quantity: $minimumQuantity"

        etQuantity.setText(
            minimumQuantity.toString()
        )

        // Back button

        tvBack.setOnClickListener {
            finish()
        }

        // Add to cart button

        btnAddToCart.setOnClickListener {

            val quantityText =
                etQuantity.text.toString().trim()

            if (quantityText.isEmpty()) {

                etQuantity.error =
                    "Please enter a quantity"

                etQuantity.requestFocus()

                return@setOnClickListener
            }

            val quantity =
                quantityText.toIntOrNull()

            if (quantity == null) {

                etQuantity.error =
                    "Please enter a valid number"

                etQuantity.requestFocus()

                return@setOnClickListener
            }

            if (quantity < minimumQuantity) {

                etQuantity.error =
                    "Minimum quantity is $minimumQuantity"

                etQuantity.requestFocus()

                Toast.makeText(
                    this,
                    "Please order at least $minimumQuantity",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Create product for cart

            val product = Product(
                id = productId,
                name = productName,
                description = description,
                category = category,
                price = productPrice,
                minimumQuantity = minimumQuantity,
                imageResId = productImage
            )

            CartManager.addItem(
                product,
                quantity
            )

            Toast.makeText(
                this,
                "$productName added to cart.",
                Toast.LENGTH_SHORT
            ).show()

            val cartIntent = Intent(
                this,
                CartActivity::class.java
            )

            startActivity(cartIntent)
        }
    }
}
