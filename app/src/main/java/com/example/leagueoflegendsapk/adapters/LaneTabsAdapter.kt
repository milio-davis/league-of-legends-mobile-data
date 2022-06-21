package com.example.leagueoflegendsapk.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.leagueoflegendsapk.fragments.HomeLanesFragment

class LaneTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        val ARG_OBJECT = "object"

        // Return a NEW fragment instance in createFragment(int)
        val fragment = HomeLanesFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }
}