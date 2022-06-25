package com.example.leagueoflegendsapk.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.api.RetrofitManager
import com.example.leagueoflegendsapk.database.ChampionDAO
import com.example.leagueoflegendsapk.database.DB
import com.example.leagueoflegendsapk.database.DBChampionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private var db: DB? = null
    private var championDAO: ChampionDAO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        db = DB.getAppDataBase(applicationContext)
        championDAO = db?.getChampionDAO()

        fetchChampionsToDB(this)
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

    private fun fetchChampionsToDB(activity: Activity) {
        val a = RetrofitManager()
        CoroutineScope(Dispatchers.IO).launch {
            async { a.getChampions(activity) { x -> Log.d("RETRO TEST SPLASH", x.toString())} }
        }

        championDAO?.insertChampion(DBChampionEntity(1, "asiufh", "ajks","asd"))
        Log.d("DBTEST", championDAO!!.loadChampionById(1).toString())
        val champions = championDAO?.getAll() as List<DBChampionEntity>
        for ( champion in champions){
            Log.d("DBTEST", champion.name)
        }
    }

}