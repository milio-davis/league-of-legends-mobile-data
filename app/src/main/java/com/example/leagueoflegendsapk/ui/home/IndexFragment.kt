package com.example.leagueoflegendsapk.ui.home

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.leagueoflegendsapk.databinding.FragmentIndexBinding

class IndexFragment : Fragment() {

    private var _binding: FragmentIndexBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val indexViewModel =
                ViewModelProvider(this).get(IndexViewModel::class.java)

        _binding = FragmentIndexBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        indexViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        sharedPref = requireActivity().getSharedPreferences("lolSharedPreferences", MODE_PRIVATE)
        binding.txtSummonersNameHome.text = sharedPref!!.getString("summonersName","")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}