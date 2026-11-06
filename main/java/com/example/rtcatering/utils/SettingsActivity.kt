package com.example.rtcatering

import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.rtcatering.utils.ThemeManager

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 30, 24, 24)
            setBackgroundColor(
                if (ThemeManager.isDarkMode(this@SettingsActivity))
                    Color.rgb(35, 35, 35)
                else Color.rgb(250, 247, 243)
            )
        }

        val title = TextView(this).apply {
            text = "Settings"
            textSize = 28f
            setTextColor(
                getColor(R.color.rt_primary)
            )
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val description = TextView(this).apply {
            text = "Customise your R&T Catering experience."
            textSize = 15f
            setPadding(0, 12, 0, 25)
            setTextColor(
                if (ThemeManager.isDarkMode(this@SettingsActivity))
                    Color.WHITE else Color.DKGRAY
            )
        }

        val themeTitle = TextView(this).apply {
            text = "Appearance"
            textSize = 19f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(getColor(R.color.rt_primary))
        }

        val darkMode = Switch(this).apply {
            text = "Dark mode"
            textSize = 16f
            setTextColor(
                if (ThemeManager.isDarkMode(this@SettingsActivity))
                    Color.WHITE else Color.DKGRAY
            )
            isChecked = ThemeManager.isDarkMode(this@SettingsActivity)

            setOnCheckedChangeListener { _, checked ->
                ThemeManager.setDarkMode(
                    this@SettingsActivity,
                    checked
                )
            }
        }

        val notification = Switch(this).apply {
            text = "Order notifications"
            textSize = 16f
            setTextColor(
                if (ThemeManager.isDarkMode(this@SettingsActivity))
                    Color.WHITE else Color.DKGRAY
            )
        }

        val privacy = Button(this).apply {
            text = "Privacy and Security"
            setOnClickListener {
                Toast.makeText(
                    this@SettingsActivity,
                    "Privacy settings will be connected next.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val back = Button(this).apply {
            text = "BACK TO PROFILE"
            setOnClickListener {
                finish()
            }
        }

        layout.addView(title)
        layout.addView(description)
        layout.addView(themeTitle)
        layout.addView(darkMode)
        layout.addView(notification)
        layout.addView(privacy)
        layout.addView(back)

        setContentView(layout)
    }
}