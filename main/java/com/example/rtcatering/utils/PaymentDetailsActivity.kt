package com.example.rtcatering

import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PaymentDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 30, 24, 24)
            setBackgroundColor(Color.rgb(250, 247, 243))
        }

        val title = TextView(this).apply {
            text = "Payment Details"
            textSize = 27f
            setTextColor(getColor(R.color.rt_primary))
            setTypeface(null, Typeface.BOLD)
        }

        val info = TextView(this).apply {
            text = """
                Add your preferred payment details.

                This section is optional. You can still
                place catering orders without saving
                your banking information.
            """.trimIndent()
            textSize = 15f
            setTextColor(Color.DKGRAY)
            setPadding(0, 15, 0, 20)
        }

        val bank = EditText(this).apply {
            hint = "Bank name"
        }

        val accountHolder = EditText(this).apply {
            hint = "Account holder name"
        }

        val accountNumber = EditText(this).apply {
            hint = "Account number"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }

        val save = Button(this).apply {
            text = "SAVE PAYMENT DETAILS"

            setOnClickListener {
                if (bank.text.isBlank() ||
                    accountHolder.text.isBlank() ||
                    accountNumber.text.isBlank()
                ) {
                    Toast.makeText(
                        this@PaymentDetailsActivity,
                        "Please complete all fields.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@PaymentDetailsActivity,
                        "Payment interface ready. Secure payment storage must be connected before saving.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        val back = Button(this).apply {
            text = "BACK"
            setOnClickListener {
                finish()
            }
        }

        layout.addView(title)
        layout.addView(info)
        layout.addView(bank)
        layout.addView(accountHolder)
        layout.addView(accountNumber)
        layout.addView(save)
        layout.addView(back)

        setContentView(layout)
    }
}