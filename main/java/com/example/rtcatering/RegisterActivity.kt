
package com.example.rtcatering

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rtcatering.api.AuthResponse
import com.example.rtcatering.api.RegisterRequest
import com.example.rtcatering.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        btnRegister = findViewById(R.id.btnRegister)

        val tvBack = findViewById<TextView>(R.id.tvBack)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnRegister.setOnClickListener {
            registerUser()
        }

        tvBack.setOnClickListener {
            finish()
        }

        tvLogin.setOnClickListener {
            openLogin()
        }
    }

    private fun registerUser() {

        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim().lowercase()
        val phone = etPhone.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // First name
        if (firstName.isEmpty()) {
            etFirstName.error = "Please enter your first name"
            etFirstName.requestFocus()
            return
        }

        // Last name
        if (lastName.isEmpty()) {
            etLastName.error = "Please enter your last name"
            etLastName.requestFocus()
            return
        }

        // Email
        if (email.isEmpty() ||
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ) {
            etEmail.error = "Please enter a valid email address"
            etEmail.requestFocus()
            return
        }

        // Phone
        if (phone.isEmpty()) {
            etPhone.error = "Please enter your phone number"
            etPhone.requestFocus()
            return
        }

        // Password
        if (password.length < 6) {
            etPassword.error = "Password must be at least 6 characters"
            etPassword.requestFocus()
            return
        }

        // Confirm password
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.error = "Please confirm your password"
            etConfirmPassword.requestFocus()
            return
        }

        if (password != confirmPassword) {
            etConfirmPassword.error = "Passwords do not match"
            etConfirmPassword.requestFocus()
            return
        }

        // Combine first and last name
        val fullName = "$firstName $lastName"

        // Create registration request
        val request = RegisterRequest(
            fullName,
            email,
            phone,
            password
        )

        // Disable button while registering
        btnRegister.isEnabled = false
        btnRegister.text = "REGISTERING..."

        // Send registration details to API
        RetrofitClient.apiService.register(request)
            .enqueue(object : Callback<AuthResponse> {

                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {

                    btnRegister.isEnabled = true
                    btnRegister.text = "REGISTER"

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@RegisterActivity,
                            "Account created successfully! Please log in.",
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(
                            this@RegisterActivity,
                            LoginActivity::class.java
                        )

                        startActivity(intent)
                        finish()

                    } else {

                        val errorMessage = response.errorBody()
                            ?.string()
                            ?.takeIf { it.isNotBlank() }
                            ?: response.message()

                        Toast.makeText(
                            this@RegisterActivity,
                            "Registration failed (${response.code()}): $errorMessage",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<AuthResponse>,
                    t: Throwable
                ) {

                    btnRegister.isEnabled = true
                    btnRegister.text = "REGISTER"

                    Toast.makeText(
                        this@RegisterActivity,
                        "Connection error: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun openLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        finish()
    }
}
