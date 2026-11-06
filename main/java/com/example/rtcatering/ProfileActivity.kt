
package com.example.rtcatering

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.rtcatering.utils.CustomerSession
import com.example.rtcatering.utils.ThemeManager
import java.io.File
import java.io.FileOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var removePhoto: Button

    private val userId by lazy {
        getSharedPreferences("customer_session", MODE_PRIVATE)
            .getInt("userId", 0)
    }

    private val photoFileName: String
        get() = "customer_profile_$userId.jpg"

    private val photoPicker = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            saveProfilePhoto(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applyTheme(this)
        super.onCreate(savedInstanceState)

        createProfileScreen()
        loadProfilePhoto()
    }

    private fun createProfileScreen() {

        val dark = ThemeManager.isDarkMode(this)

        val background = if (dark) {
            Color.rgb(35, 35, 35)
        } else {
            Color.rgb(250, 247, 243)
        }

        val textColor = if (dark) Color.WHITE else Color.DKGRAY

        val scroll = ScrollView(this)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24.dp, 25.dp, 24.dp, 30.dp)
            setBackgroundColor(background)
        }

        val title = TextView(this).apply {
            text = "My Profile"
            textSize = 29f
            setTypeface(null, Typeface.BOLD)
            setTextColor(getColor(R.color.rt_primary))
            gravity = Gravity.CENTER
        }

        val welcome = TextView(this).apply {
            text = "Welcome to R&T Halaal Catering"
            textSize = 15f
            setTextColor(textColor)
            gravity = Gravity.CENTER
            setPadding(0, 8.dp, 0, 22.dp)
        }

        // Profile picture - created only once
        profileImage = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(125.dp, 125.dp)
            scaleType = ImageView.ScaleType.CENTER_CROP

            setBackgroundResource(android.R.drawable.picture_frame)

            clipToOutline = true
            setImageResource(android.R.drawable.ic_menu_camera)
            contentDescription = "Customer profile picture"
        }

        val imageContainer = LinearLayout(this).apply {
            gravity = Gravity.CENTER
            addView(profileImage)
        }

        val changePhoto = actionButton("CHANGE PROFILE PICTURE") {
            photoPicker.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }

        removePhoto = actionButton("REMOVE PICTURE") {
            removeProfilePhoto()
        }

        // Account information
        val accountHeading = heading("Account Information")

        val name = information(
            "Full Name",
            CustomerSession.getName(this),
            textColor
        )

        val email = information(
            "Email Address",
            CustomerSession.getEmail(this),
            textColor
        )

        val phone = information(
            "Phone Number",
            CustomerSession.getPhone(this).ifBlank {
                "Not provided"
            },
            textColor
        )

        val editDetails = actionButton("EDIT ACCOUNT DETAILS") {
            Toast.makeText(
                this,
                "Account editing will be connected to your API next.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Payments
        val paymentHeading = heading("Payments")

        val paymentButton = actionButton("MANAGE PAYMENT DETAILS") {
            startActivity(
                Intent(this, PaymentDetailsActivity::class.java)
            )
        }

        // Settings
        val settingsHeading = heading("Preferences & Settings")

        val settingsButton = actionButton("OPEN SETTINGS") {
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
        }

        val securityButton = actionButton("PRIVACY & SECURITY") {
            Toast.makeText(
                this,
                "Security options will be connected next.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Logout
        val logoutButton = actionButton("LOG OUT") {
            showLogoutConfirmation()
        }

        val backButton = actionButton("BACK TO HOME") {
            finish()
        }

        // Add views to screen
        layout.addView(title)
        layout.addView(welcome)
        layout.addView(imageContainer)
        layout.addView(changePhoto)
        layout.addView(removePhoto)

        layout.addView(accountHeading)
        layout.addView(name)
        layout.addView(email)
        layout.addView(phone)
        layout.addView(editDetails)

        layout.addView(paymentHeading)
        layout.addView(paymentButton)

        layout.addView(settingsHeading)
        layout.addView(settingsButton)
        layout.addView(securityButton)

        layout.addView(logoutButton)
        layout.addView(backButton)

        scroll.addView(layout)
        setContentView(scroll)
    }

    private fun heading(value: String): TextView {
        return TextView(this).apply {
            text = value
            textSize = 20f
            setTypeface(null, Typeface.BOLD)
            setTextColor(getColor(R.color.rt_primary))
            setPadding(0, 25.dp, 0, 10.dp)
        }
    }

    private fun information(
        label: String,
        value: String,
        textColor: Int
    ): TextView {

        return TextView(this).apply {
            text = "$label\n$value"
            textSize = 15f
            setTextColor(textColor)
            setPadding(12.dp, 12.dp, 12.dp, 12.dp)
        }
    }

    private fun actionButton(
        label: String,
        action: () -> Unit
    ): Button {

        return Button(this).apply {
            text = label
            textSize = 13f
            setOnClickListener {
                action()
            }
        }
    }

    private fun showLogoutConfirmation() {

        android.app.AlertDialog.Builder(this)
            .setTitle("Log out")
            .setMessage("Are you sure you want to log out?")
            .setNegativeButton("CANCEL", null)
            .setPositiveButton("LOG OUT") { _, _ ->

                CustomerSession.logout(this)

                val intent = Intent(
                    this,
                    LoginActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                startActivity(intent)
                finish()
            }
            .show()
    }

    private fun saveProfilePhoto(uri: Uri) {

        try {
            val destination = File(filesDir, photoFileName)

            contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(destination).use { output ->
                    input.copyTo(output)
                }
            } ?: throw Exception("Image unavailable")

            loadProfilePhoto()

            Toast.makeText(
                this,
                "Profile picture updated!",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Could not save profile picture.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun loadProfilePhoto() {

        val file = File(filesDir, photoFileName)

        if (file.exists()) {
            profileImage.setImageURI(Uri.fromFile(file))
            removePhoto.visibility = View.VISIBLE
        } else {
            profileImage.setImageResource(
                android.R.drawable.ic_menu_camera
            )
            removePhoto.visibility = View.GONE
        }
    }

    private fun removeProfilePhoto() {

        File(filesDir, photoFileName).delete()

        loadProfilePhoto()

        Toast.makeText(
            this,
            "Profile picture removed.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}
