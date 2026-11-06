package com.example.rtcatering.utils

import android.content.Context

object CustomerSession {

    private const val PREFS = "customer_session"

    fun saveCustomer(
        context: Context,
        userId: Int,
        name: String,
        email: String,
        phone: String = ""
    ) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt("userId", userId)
            .putString("name", name)
            .putString("email", email)
            .putString("phone", phone)
            .putBoolean("loggedIn", true)
            .apply()
    }

    fun getName(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString("name", "Customer") ?: "Customer"

    fun getEmail(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString("email", "") ?: ""

    fun getPhone(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString("phone", "") ?: ""

    fun logout(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}