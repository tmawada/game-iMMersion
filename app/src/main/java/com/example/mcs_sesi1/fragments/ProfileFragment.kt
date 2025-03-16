package com.example.mcs_sesi1.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.mcs_sesi1.MainActivity
import com.example.mcs_sesi1.database.DatabaseHelper
import com.example.mcs_sesi1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val sharedPref = requireActivity().getSharedPreferences("userPref", android.content.Context.MODE_PRIVATE)
//        val username = sharedPref.getString("username", "Guest")
//        binding.tvUsername.text = "Hello, $username"

        binding.btnLogout.setOnClickListener {
            logoutUser()
        }

        binding.btnRemoveAcc.setOnClickListener {
            showRemoveAccountDialog()
        }
    }

    private fun logoutUser() {
        Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showRemoveAccountDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Remove Account")
            .setMessage("Are you sure you want to remove your account? This action cannot be undone.")
            .setPositiveButton("Yes") { _, _ ->
                removeAccount()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun removeAccount() {
        val sharedPref = requireActivity().getSharedPreferences("userPref", android.content.Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Guest")

        dbHelper.deleteAccount(username.toString())
        Toast.makeText(requireContext(), "Account removed", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
