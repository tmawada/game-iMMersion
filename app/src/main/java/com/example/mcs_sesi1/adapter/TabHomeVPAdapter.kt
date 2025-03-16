package com.example.mcs_sesi1.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mcs_sesi1.fragments.ChatFragment
import com.example.mcs_sesi1.fragments.LocationFragment
import com.example.mcs_sesi1.fragments.ProfileFragment
import com.example.mcs_sesi1.fragments.RentalFragment

class TabHomeVPAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0-> RentalFragment()
            1-> LocationFragment()
            2-> ChatFragment()
            3-> ProfileFragment()
            else -> RentalFragment()
        }
    }

}