package com.example.mcs_sesi1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mcs_sesi1.databinding.FragmentRentalBinding
import com.example.mcs_sesi1.models.RentalRoom
import com.example.mcs_sesi1.adapter.RentalAdapter
import com.example.mcs_sesi1.database.DatabaseRental

class RentalFragment : Fragment() {

    private lateinit var binding: FragmentRentalBinding
    private lateinit var requestQueue: RequestQueue
    private lateinit var dbHelper: DatabaseRental

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRentalBinding.inflate(inflater, container, false)
        dbHelper = DatabaseRental(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestQueue = Volley.newRequestQueue(requireContext())
        binding.rvRental.layoutManager = LinearLayoutManager(requireContext())
        getRooms()
    }

    private fun refresh() {
        if (isAdded) {
            val roomList = dbHelper.getAllRooms()
            val adapter = RentalAdapter(roomList)
            binding.rvRental.adapter = adapter
        }
    }

    private fun getRooms() {
        val url = "https://api.npoint.io/cada4450377e9fde05de"

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val itemArray = response.getJSONArray("rooms")

                dbHelper.clearRooms()

                for (i in 0 until itemArray.length()) {
                    val itemObj = itemArray.getJSONObject(i)

                    val item = RentalRoom(
                        itemObj.getString("roomId"),
                        itemObj.getString("console"),
                        itemObj.getInt("price"),
                        itemObj.getBoolean("status")
                    )
                    dbHelper.addRoom(item)
                }

                refresh()
                if (isAdded) {
                    Toast.makeText(requireContext(), "Data fetched and stored!", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Failed to get data: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }, { error ->
            Log.e("Volley error", error.toString())
        })

        requestQueue.add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}

