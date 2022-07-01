package com.example.leagueoflegendsapk.ui.configuration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.leagueoflegendsapk.activities.FirstActivity
import com.example.leagueoflegendsapk.databinding.FragmentConfigurationBinding


class ConfigurationFragment : Fragment() {

    private var _binding: FragmentConfigurationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val configurationViewModel =
            ViewModelProvider(this).get(ConfigurationViewModel::class.java)

        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)

        binding.btnLogoutConfig.setOnClickListener {
            removeSharedPrefData()
            startActivity(Intent(requireContext(), FirstActivity::class.java))
            requireActivity().finish()
        }

        /*
        val textView: TextView = binding.textNotifications
        configurationViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

         */
        return binding.root
    }

    private fun removeSharedPrefData() {
        val sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove("summonersName")
        editor.remove("summonerId")
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}