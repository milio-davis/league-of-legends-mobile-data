package com.example.leagueoflegendsapk.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.activities.MainActivity
import com.example.leagueoflegendsapk.api.RetrofitManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            setSummonersNameSharedPref(view.findViewById<TextView>(R.id.txtSummonersNameLogin).text.toString())
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setSummonersNameSharedPref(summonersName: String) {
        val editor = requireContext().getSharedPreferences("lolSharedPreferences", AppCompatActivity.MODE_PRIVATE).edit()
        editor.putString("summonersName", summonersName)
        editor.apply()
    }

}