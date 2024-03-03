package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        val emailEditText = EditText(this)
        emailEditText.hint = "Email"
        layout.addView(emailEditText)

        val passwordEditText = EditText(this)
        passwordEditText.hint = "Password"
        passwordEditText.inputType =
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        layout.addView(passwordEditText)

        val loginButton = Button(this)
        loginButton.text = "Login"
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            performLogin(email, password)
        }
        layout.addView(loginButton)

        val openSecondActivityButton = Button(this)
        openSecondActivityButton.text = "Open Second Activity"
        openSecondActivityButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        layout.addView(openSecondActivityButton)

        setContentView(layout)
    }

    private fun performLogin(email: String, password: String) {

        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
    }
}