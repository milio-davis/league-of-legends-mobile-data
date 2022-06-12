package com.example.leagueoflegendsapk.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.leagueoflegendsapk.R


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val txtSummonersName: TextView = findViewById(R.id.txtSummonersNameLogin)
            setSummonersNameSharedPreferences(txtSummonersName.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setSummonersNameSharedPreferences(summonersName: String) {
        val editor = getSharedPreferences("lolSharedPreferences", MODE_PRIVATE).edit()
        editor.putString("summonersName", summonersName)
        editor.apply()
    }
}