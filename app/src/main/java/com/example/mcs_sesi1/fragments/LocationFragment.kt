package com.example.mcs_sesi1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mcs_sesi1.R
import com.example.mcs_sesi1.databinding.FragmentLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val location1 = LatLng(-6.1751, 106.7904)
        val location2 = LatLng(-6.1875, 106.8063)
        val location3 = LatLng(-6.1627, 106.7758)

        map.addMarker(MarkerOptions().position(location1).title("- Cabang 1"))
        map.addMarker(MarkerOptions().position(location2).title("- Cabang 2"))
        map.addMarker(MarkerOptions().position(location3).title("- Cabang 3"))

        val bounds = LatLngBounds.builder()
            .include(location1)
            .include(location2)
            .include(location3)
            .build()

        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
