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
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.activities.FirstActivity
import com.example.leagueoflegendsapk.databinding.FragmentConfigurationBinding
import com.squareup.picasso.Picasso


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

        /*
        val textView: TextView = binding.textNotifications
        configurationViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        */

        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)

        sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)

        binding.btnLogoutConfig.setOnClickListener {
            removeSharedPrefData()
            startActivity(Intent(requireContext(), FirstActivity::class.java))
            requireActivity().finish()
        }

        binding.txtSummonerNameConfig.text =
           "${sharedPref.getString("summonersName", "")!!}"
        binding.txtSummonerLevelConfig.text =
            "${resources.getString(R.string.title_level)}: ${sharedPref.getInt("summonerLevel", 1)}"
        loadProfilePic(sharedPref.getInt("profileIconId", 1))

        if (sharedPref.getBoolean("nightMode", false)) {
            binding.switchThemeConfig.isChecked = true
        }

        binding.switchThemeConfig.setOnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPref.edit().putBoolean("nightMode", true).apply()
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPref.edit().putBoolean("nightMode", false).apply()
            }
        }

        return binding.root
    }

    private fun loadProfilePic(profilePicId: Int) {
        val path = "https://ddragon.leagueoflegends.com/cdn/11.14.1/img/profileicon/${profilePicId}.png"
        Picasso.get().load(path).into(binding.imgSummonerProfileConfig)
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