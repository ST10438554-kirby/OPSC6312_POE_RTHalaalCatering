
package com.example.rtcatering

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rtcatering.data.OfflineSyncManager

class HomeActivity : AppCompatActivity() {

    private lateinit var offlineSyncManager: OfflineSyncManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        // Initialise offline synchronisation
        offlineSyncManager = OfflineSyncManager(applicationContext)

        val btnBrowseMenu = findViewById<Button>(R.id.btnBrowseMenu)
        val btnPopularPlatters = findViewById<LinearLayout>(R.id.btnPopularPlatters)
        val btnPopularPackages = findViewById<LinearLayout>(R.id.btnPopularPackages)

        val navHome = findViewById<TextView>(R.id.navHome)
        val navMenu = findViewById<TextView>(R.id.navMenu)
        val navCart = findViewById<TextView>(R.id.navCart)
        val navOrders = findViewById<TextView>(R.id.navOrders)
        val navProfile = findViewById<TextView>(R.id.navProfile)

        // Browse menu
        btnBrowseMenu.setOnClickListener {
            startActivity(Intent(this, ProductsActivity::class.java))
        }

        // Popular platters
        btnPopularPlatters.setOnClickListener {
            Toast.makeText(
                this,
                "Platters selected.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Popular packages
        btnPopularPackages.setOnClickListener {
            startActivity(Intent(this, ProductsActivity::class.java))
        }

        // Home navigation
        navHome.setOnClickListener {
            // Already on Home
        }

        // Menu navigation
        navMenu.setOnClickListener {
            startActivity(Intent(this, ProductsActivity::class.java))
        }

        // Cart navigation
        navCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // Orders navigation
        navOrders.setOnClickListener {
            startActivity(Intent(this, MyOrdersActivity::class.java))
        }

        // Profile navigation
        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        // Automatically synchronise pending orders when online
        if (::offlineSyncManager.isInitialized) {
            offlineSyncManager.syncPendingOrders()
        }
    }
}
