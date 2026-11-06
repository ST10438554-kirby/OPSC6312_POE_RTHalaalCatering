package com.example.rtcatering

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rtcatering.utils.CartManager

class CartActivity : AppCompatActivity() {

    private lateinit var cartContainer: LinearLayout
    private lateinit var tvCartTotal: TextView
    private lateinit var tvEmptyCart: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cart)

        cartContainer = findViewById(R.id.cartContainer)
        tvCartTotal = findViewById(R.id.tvCartTotal)
        tvEmptyCart = findViewById(R.id.tvEmptyCart)

        val tvBack = findViewById<TextView>(R.id.tvBack)
        val btnContinueShopping = findViewById<Button>(R.id.btnContinueShopping)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        tvBack.setOnClickListener {
            finish()
        }

        btnContinueShopping.setOnClickListener {
            val intent = Intent(this, ProductsActivity::class.java)
            startActivity(intent)
        }

        btnCheckout.setOnClickListener {

            if (CartManager.items.isEmpty()) {

                Toast.makeText(
                    this,
                    "Your cart is empty.",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val intent =
                    Intent(this, CheckoutActivity::class.java)

                startActivity(intent)
            }
        }

        displayCart()
    }

    override fun onResume() {
        super.onResume()
        displayCart()
    }

    private fun displayCart() {

        cartContainer.removeAllViews()

        if (CartManager.items.isEmpty()) {

            tvEmptyCart.visibility = TextView.VISIBLE

        } else {

            tvEmptyCart.visibility = TextView.GONE

            for (cartItem in CartManager.items.toList()) {

                val itemView = layoutInflater.inflate(
                    R.layout.item_cart,
                    cartContainer,
                    false
                )

                val tvProductName =
                    itemView.findViewById<TextView>(R.id.tvCartProductName)

                val tvCategory =
                    itemView.findViewById<TextView>(R.id.tvCartCategory)

                val tvPrice =
                    itemView.findViewById<TextView>(R.id.tvCartPrice)

                val etQuantity =
                    itemView.findViewById<EditText>(R.id.etCartQuantity)

                val tvSubtotal =
                    itemView.findViewById<TextView>(R.id.tvCartSubtotal)

                val btnUpdate =
                    itemView.findViewById<Button>(R.id.btnUpdateQuantity)

                val btnRemove =
                    itemView.findViewById<Button>(R.id.btnRemove)

                tvProductName.text = cartItem.product.name

                tvCategory.text = cartItem.product.category

                tvPrice.text =
                    String.format(
                        "R %.2f each",
                        cartItem.product.price
                    )

                etQuantity.setText(
                    cartItem.quantity.toString()
                )

                tvSubtotal.text =
                    String.format(
                        "Subtotal: R %.2f",
                        cartItem.getSubtotal()
                    )

                btnUpdate.setOnClickListener {

                    val quantityText =
                        etQuantity.text.toString().trim()

                    val quantity =
                        quantityText.toIntOrNull()

                    if (quantity == null || quantity <= 0) {

                        etQuantity.error =
                            "Enter a valid quantity"

                        return@setOnClickListener
                    }

                    if (quantity < cartItem.product.minimumQuantity) {

                        etQuantity.error =
                            "Minimum quantity is ${cartItem.product.minimumQuantity}"

                        Toast.makeText(
                            this,
                            "Minimum quantity is ${cartItem.product.minimumQuantity}",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@setOnClickListener
                    }

                    CartManager.updateQuantity(
                        cartItem.product.id,
                        quantity
                    )

                    Toast.makeText(
                        this,
                        "Quantity updated.",
                        Toast.LENGTH_SHORT
                    ).show()

                    displayCart()
                }

                btnRemove.setOnClickListener {

                    CartManager.removeItem(
                        cartItem.product.id
                    )

                    Toast.makeText(
                        this,
                        "${cartItem.product.name} removed from cart.",
                        Toast.LENGTH_SHORT
                    ).show()

                    displayCart()
                }

                cartContainer.addView(itemView)
            }
        }

        tvCartTotal.text =
            String.format(
                "R %.2f",
                CartManager.getTotal()
            )
    }
}