
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
import com.example.rtcatering.api.LoginRequest
import com.example.rtcatering.api.RetrofitClient
import com.example.rtcatering.utils.CustomerSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        val tvBack = findViewById<TextView>(R.id.tvBack)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        btnLogin.setOnClickListener {
            loginUser()
        }

        tvBack.setOnClickListener {
            finish()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        tvForgotPassword.setOnClickListener {
            Toast.makeText(
                this,
                "Password recovery will be added later.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun loginUser() {

        val email = etEmail.text.toString().trim().lowercase()
        val password = etPassword.text.toString()

        if (email.isEmpty()) {
            etEmail.error = "Enter your email"
            etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email"
            etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "Enter your password"
            etPassword.requestFocus()
            return
        }

        btnLogin.isEnabled = false
        btnLogin.text = "LOGGING IN..."

        val request = LoginRequest(email, password)

        RetrofitClient.apiService.login(request)
            .enqueue(object : Callback<AuthResponse> {

                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {

                    btnLogin.isEnabled = true
                    btnLogin.text = "LOGIN"

                    if (response.isSuccessful) {

                        val customer = response.body()

                        if (customer == null || customer.userId == null) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login response is missing customer details.",
                                Toast.LENGTH_LONG
                            ).show()
                            return
                        }

                        CustomerSession.saveCustomer(
                            this@LoginActivity,
                            customer.userId,
                            customer.fullName ?: "Customer",
                            customer.email ?: email,
                            customer.phone ?: ""
                        )

                        Toast.makeText(
                            this@LoginActivity,
                            "Welcome back!",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(
                            this@LoginActivity,
                            HomeActivity::class.java
                        )

                        startActivity(intent)
                        finish()

                    } else {

                        val errorMessage = response.errorBody()
                            ?.string()
                            ?.takeIf { it.isNotBlank() }
                            ?: response.message()

                        Toast.makeText(
                            this@LoginActivity,
                            "Login failed (${response.code()}): $errorMessage",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<AuthResponse>,
                    t: Throwable
                ) {

                    btnLogin.isEnabled = true
                    btnLogin.text = "LOGIN"

                    Toast.makeText(
                        this@LoginActivity,
                        "Connection error: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
