package com.example.leagueoflegendsapk.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.leagueoflegendsapk.R

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
    }

    private fun setSummonersNameSharedPref(summonersName: String) {
        val editor = requireContext().getSharedPreferences("lolSharedPreferences", AppCompatActivity.MODE_PRIVATE).edit()
        editor.putString("summonersName", summonersName)
        editor.putBoolean("nightMode", false)
        editor.apply()
    }

}