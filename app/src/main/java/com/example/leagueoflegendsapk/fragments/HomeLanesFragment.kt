package com.example.leagueoflegendsapk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.leagueoflegendsapk.databinding.FragmentHomeLanesBinding

class HomeLanesFragment : Fragment() {

    private lateinit var binding: FragmentHomeLanesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeLanesBinding.inflate(layoutInflater)

        val a = requireArguments().getInt("position",-1)
        binding.txtChamp.text = a.toString()

        return binding.root
    }
}