
package com.example.rtcatering

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rtcatering.models.Product

class ProductsActivity : AppCompatActivity() {

    private val products = listOf(

        Product(
            1,
            "Milk Tart",
            "Traditional creamy milk tart with a delicious cinnamon topping.",
            "Sweet Treats",
            5.00,
            20,
            R.drawable.milktarts
        ),

        Product(
            2,
            "Hertzogies",
            "Sweet pastry filled with coconut and apricot jam.",
            "Sweet Treats",
            6.00,
            20,
            R.drawable.hertzogies
        ),

        Product(
            3,
            "Cream Fancies",
            "Delicious cream-filled sweet treats perfect for events.",
            "Sweet Treats",
            8.00,
            20,
            R.drawable.creamfancies
        ),

        Product(
            4,
            "Samoosas",
            "Crispy savoury samoosas filled with a delicious spiced filling.",
            "Savoury Treats",
            4.00,
            20,
            R.drawable.samoosa
        ),

        Product(
            5,
            "Cocktail Rolls",
            "Mini savoury rolls suitable for parties and functions.",
            "Savoury Treats",
            5.00,
            20,
            R.drawable.sausagerolls
        ),

        Product(
            6,
            "Party Platter",
            "A selection of sweet and savoury treats prepared for your event.",
            "Platters",
            450.00,
            1,
            R.drawable.platters
        ),

        Product(
            7,
            "Chicken Catering Pot",
            "A hearty chicken dish prepared for larger events.",
            "Catering Pots",
            850.00,
            1,
            R.drawable.chickenakni
        ),

        Product(
            8,
            "Event Catering Package",
            "A catering package designed for birthdays, weddings and special events.",
            "Packages",
            1500.00,
            1,
            R.drawable.function
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_products)

        val tvBack = findViewById<TextView>(R.id.tvBack)

        tvBack.setOnClickListener {
            finish()
        }

        displayProducts()
    }

    private fun displayProducts() {

        val productContainer =
            findViewById<LinearLayout>(R.id.productContainer)

        for (product in products) {

            val productView = layoutInflater.inflate(
                R.layout.item_product,
                productContainer,
                false
            )

            val ivProductImage =
                productView.findViewById<ImageView>(R.id.ivProductImage)

            val tvName =
                productView.findViewById<TextView>(R.id.tvProductName)

            val tvCategory =
                productView.findViewById<TextView>(R.id.tvProductCategory)

            val tvDescription =
                productView.findViewById<TextView>(R.id.tvProductDescription)

            val tvPrice =
                productView.findViewById<TextView>(R.id.tvProductPrice)

            val tvMinimum =
                productView.findViewById<TextView>(R.id.tvMinimumQuantity)

            val tvViewDetails =
                productView.findViewById<TextView>(R.id.tvViewDetails)

            // Display product image
            if (product.imageResId != 0) {
                ivProductImage.setImageResource(product.imageResId)
            }

            tvName.text = product.name
            tvCategory.text = product.category
            tvDescription.text = product.description

            tvPrice.text = String.format(
                "R %.2f",
                product.price
            )

            tvMinimum.text =
                "Minimum quantity: ${product.minimumQuantity}"

            tvViewDetails.setOnClickListener {

                val intent = Intent(
                    this,
                    ProductDetailsActivity::class.java
                )

                intent.putExtra("productId", product.id)
                intent.putExtra("productName", product.name)
                intent.putExtra("productDescription", product.description)
                intent.putExtra("productCategory", product.category)
                intent.putExtra("productPrice", product.price)
                intent.putExtra("minimumQuantity", product.minimumQuantity)
                intent.putExtra("productImage", product.imageResId)

                startActivity(intent)
            }

            productContainer.addView(productView)
        }
    }
}
