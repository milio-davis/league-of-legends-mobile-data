package com.example.leagueoflegendsapk.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.leagueoflegendsapk.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        fetchChampionsRequest()
        advanceActivityByTimeout(3000)
    }

    private fun advanceActivityByTimeout(splashTimeout: Long) {
        Handler().postDelayed({
            var sharedPref = getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)
            val intent = if (sharedPref.getString("summonersName", "") == "")
                Intent(this, LoginActivity::class.java)
            else
                Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        }, splashTimeout)
    }

    private fun fetchChampionsRequest() {
    }

}