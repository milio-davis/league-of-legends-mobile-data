package com.example.leagueoflegendsapk.api

import android.app.Activity
import android.util.Log
import com.example.leagueoflegendsapk.api.interfaces.RiotAPI
import com.example.leagueoflegendsapk.entities.Champion
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    private val apiKey = "RGAPI-f0abcc1c-6bbe-48db-b438-ebc17082fd41"
    private val summonerId = "6nwa1pkSeo2yUWI0gIFiD2wHVw21C71NQ2NyhnxN7B_XyZA"

    private fun getRetrofitRiot(): Retrofit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://la2.api.riotgames.com/lol/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRetrofitDdragon(): Retrofit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://ddragon.leagueoflegends.com/cdn/12.11.1/data/es_MX/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private suspend fun getTopMasteryChampions(activity: Activity) {
        val call = getRetrofitRiot().create(RiotAPI::class.java).getTopMasteryChampions("champion-mastery/v4/champion-masteries/by-summoner/$summonerId?api_key=$apiKey")
        val champions = call.body()
        activity.runOnUiThread {
            if (call.isSuccessful) {
                Log.d("RETRO TEST", champions.toString())
            } else {
                Log.d("Error Retrofit", "Champions error")
            }
        }
    }

    suspend fun getChampions(activity: Activity, callBack: (Map<Any, Champion>) -> Unit) {
        val call = getRetrofitDdragon().create(RiotAPI::class.java).getChampions("champion.json")
        val champions = call.body()
        activity.runOnUiThread {
            if (call.isSuccessful) {
                @Suppress("UNCHECKED_CAST")
                val c = champions?.championsObj as? Map<Any, Champion>
                if (c != null) callBack(c)
            } else {
                Log.d("Error Retrofit", "Champions error")
            }
        }
    }

    private suspend fun getFreeChampionIds(activity: Activity, callBack: (List<String>) -> Unit) {
        val call = getRetrofitRiot().create(RiotAPI::class.java).getWeeklyChampionRotation("platform/v3/champion-rotations?api_key=$apiKey")
        val callChampList = call.body()
        activity.runOnUiThread {
            if (call.isSuccessful) {
                callBack(callChampList?.freeChampionIds ?: emptyList())
            } else {
                Log.d("Error Retrofit", "Free Champion Rotation error")
            }
        }
    }

}