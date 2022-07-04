package com.example.leagueoflegendsapk.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.leagueoflegendsapk.ui.index.HomeLanesFragment

class LaneTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        // Return a NEW fragment instance in createFragment(int)
        val fragment = HomeLanesFragment()
        fragment.arguments = Bundle().apply {
            putInt("position", position)
        }
        return fragment
    }
}