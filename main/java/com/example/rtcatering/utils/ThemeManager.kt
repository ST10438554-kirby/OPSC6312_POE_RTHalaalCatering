package com.example.rtcatering.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREFS = "app_settings"
    private const val THEME = "dark_mode"

    fun applyTheme(context: Context) {
        val dark = context.getSharedPreferences(
            PREFS, Context.MODE_PRIVATE
        ).getBoolean(THEME, false)

        AppCompatDelegate.setDefaultNightMode(
            if (dark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun setDarkMode(context: Context, enabled: Boolean) {
        context.getSharedPreferences(
            PREFS, Context.MODE_PRIVATE
        ).edit().putBoolean(THEME, enabled).apply()

        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun isDarkMode(context: Context): Boolean {
        return context.getSharedPreferences(
            PREFS, Context.MODE_PRIVATE
        ).getBoolean(THEME, false)
    }
}