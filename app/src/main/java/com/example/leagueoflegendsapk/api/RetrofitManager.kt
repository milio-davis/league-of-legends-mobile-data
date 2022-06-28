package com.example.leagueoflegendsapk.api

import android.app.Activity
import android.util.Log
import com.example.leagueoflegendsapk.api.interfaces.RiotAPI
import com.example.leagueoflegendsapk.entities.Champion
import com.example.leagueoflegendsapk.entities.ChampionMastery
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    private val apiKey = "RGAPI-608816ad-6fcf-4874-a3c8-4bae7590703a"
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

    fun getTop5MasteryChampions(callBack: (List<ChampionMastery>) -> Unit) {
        val call = getRetrofitRiot().create(RiotAPI::class.java).getTopMasteryChampions("champion-mastery/v4/champion-masteries/by-summoner/$summonerId?api_key=$apiKey")
        val response = call!!.execute().body() ?: return
        callBack(response.toList().subList(0,5) as List<ChampionMastery>)
    }

    suspend fun getChampions(activity: Activity, callBack: (List<Champion>) -> Unit) {
        val call = getRetrofitDdragon().create(RiotAPI::class.java).getChampions("champion.json")
        val data = call.body()
        activity.runOnUiThread {
            if (call.isSuccessful) {
                val champions = data?.championsObj ?: return@runOnUiThread
                val championsList = mutableListOf<Champion>()
                champions.forEach {
                    championsList.add(
                        Champion(it.value.id, it.value.name, it.value.internalName, it.value.title))
                }
                callBack(championsList.toList())
            } else {
                Log.d("Error Retrofit", "Champions error")
            }
        }
    }

    suspend fun getFreeChampionIds(activity: Activity, callBack: (List<String>) -> Unit) {
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