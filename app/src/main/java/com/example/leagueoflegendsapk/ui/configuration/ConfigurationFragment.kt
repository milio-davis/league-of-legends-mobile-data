package com.example.leagueoflegendsapk.ui.configuration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.leagueoflegendsapk.activities.FirstActivity
import com.example.leagueoflegendsapk.databinding.FragmentConfigurationBinding


class ConfigurationFragment : Fragment() {

    private var _binding: FragmentConfigurationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val configurationViewModel =
            ViewModelProvider(this).get(ConfigurationViewModel::class.java)

        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)

        sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)

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

        if (sharedPref.getBoolean("nightMode", false)) {
            binding.switchThemeConfig.isChecked = true
        }

        binding.switchThemeConfig.setOnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                requireContext().getSharedPreferences("lolSharedPreferences", AppCompatActivity.MODE_PRIVATE)
                    .edit().putBoolean("nightMode", true).apply()
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                requireContext().getSharedPreferences("lolSharedPreferences", AppCompatActivity.MODE_PRIVATE)
                    .edit().putBoolean("nightMode", false).apply()
            }
        }

        return binding.root
    }

    private fun removeSharedPrefData() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove("summonersName")
        editor.remove("summonerId")
        editor.remove("nightMode")
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}