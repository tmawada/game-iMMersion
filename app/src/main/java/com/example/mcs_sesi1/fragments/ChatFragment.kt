package com.example.mcs_sesi1.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mcs_sesi1.R
import com.example.mcs_sesi1.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)

        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.RECEIVE_SMS),
                100
            )
        }

        binding.btnSend.setOnClickListener {
            val phoneNumber = binding.etPhonenumber.text.toString()
            val message = binding.etMessage.text.toString()
            sendSms(phoneNumber, message)
        }

        return binding.root
    }

    private fun sendSms(phoneNumber: String, message: String) {
        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.SEND_SMS),
                100
            )
        } else {
            try {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                Toast.makeText(requireContext(), "SMS Sent!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to send SMS: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


}