package com.example.mcs_sesi1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mcs_sesi1.adapter.TabHomeVPAdapter
import com.example.mcs_sesi1.databinding.ActivityHomeBinding
import com.example.mcs_sesi1.fragments.ProfileFragment
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding : ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHome)

        val sharedPref = getSharedPreferences("userPref", Context.MODE_PRIVATE)

        val darkTheme = sharedPref.getBoolean("isDarkTheme", false)

        if(darkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.switchTheme.isChecked = true
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchTheme.isChecked = false
        }

        binding.switchTheme.setOnCheckedChangeListener{ _, isChecked ->
            val editor = sharedPref.edit()
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("isDarkTheme", true)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("isDarkTheme", false)
            }
            editor.apply()
        }

        val username = intent.getStringExtra("username")
        binding.tvHello.text = "Hello, $username"

        sharedPref.edit().putString("username", "$username").apply()

        binding.vpTabHome.adapter = TabHomeVPAdapter(this)

        TabLayoutMediator(binding.tlTabHome, binding.vpTabHome){ tab, position->
            tab.text = when (position){
                0-> "Rental"
                1-> "Maps"
                2-> "Chat"
                3-> "Profile"
                else -> "Rental"
            }

            tab.setIcon(
                when(position){
                    0-> R.drawable.ic_rental
                    1-> R.drawable.ic_maps
                    2-> R.drawable.ic_chat
                    3-> R.drawable.ic_profile
                    else -> R.drawable.ic_rental
                }
            )
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_profile -> Toast.makeText(this, "Profile menu clicked", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> Toast.makeText(this, "Logout menu clicked", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

}