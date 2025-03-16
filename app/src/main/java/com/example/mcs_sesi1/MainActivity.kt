package com.example.mcs_sesi1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mcs_sesi1.database.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var et_username: EditText
    private lateinit var et_password: EditText
    private lateinit var btn_login: Button
    private lateinit var tv_register: TextView
    private lateinit var tv_forgot: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)
        btn_login = findViewById(R.id.btn_login)
        tv_register = findViewById(R.id.tv_register)
        tv_forgot = findViewById(R.id.tv_forgot)

        btn_login.setOnClickListener {

            val username = et_username.text.toString().trim()
            val password = et_password.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.validateAccount(username, password)) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                Log.i("LOGIN_SUCCESS", "User: $username")

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                Log.i("LOGIN_FAILED", "Invalid login attempt for: $username")
            }
        }

        tv_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        tv_forgot.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }
}
