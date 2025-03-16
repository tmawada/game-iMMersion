package com.example.mcs_sesi1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mcs_sesi1.database.DatabaseHelper
import com.example.mcs_sesi1.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.btnRegister.setOnClickListener {

            val email = binding.etEmail.text
            val username = binding.etUsername.text
            val password = binding.etPassword.text
            val rb_male = binding.rbMale.isChecked
            val rb_female = binding.rbFemale.isChecked
            val ch_terms = binding.chTerms.isChecked

            if(email.isEmpty()){
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(username.isEmpty()){
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(password.isEmpty()){
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(!rb_male && !rb_female){
                Toast.makeText(this, "Gender must be selected", Toast.LENGTH_SHORT).show()
            }
            else if(!ch_terms){
                Toast.makeText(this, "Terms and conditions must be check", Toast.LENGTH_SHORT).show()
            }
            else{
                val builder = AlertDialog.Builder(this)

                builder.setTitle("Confirmation")
                builder.setMessage("Are you sure want to register ?")

                builder.setPositiveButton("Yes"){dialog,which ->
                    val success = dbHelper.createAccount(email.toString(), username.toString(), password.toString())

                    Log.i("REGISTER_SUCCESS", "Username: $username, Password: $password")

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                builder.setNegativeButton("No"){dialog,which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()

            }
        }
    }
}