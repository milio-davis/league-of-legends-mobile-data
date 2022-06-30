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

    private val apiKey = "RGAPI-942086ac-bb0a-4dca-bd90-867f7148bc76"

    /**
     * Build Retrofit Call for Riot Games API with BaseURL
     * (Data: Summoners, Champion rotation, Masteries)
     */
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

    /**
     * Build Retrofit Call for Data Dragon API with BaseURL
     * (Data: Champions DB)
     */
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

    /**
     * Get all champions from Data Dragon DB.
     */
    suspend fun getChampions(activity: Activity, callBack: (List<Champion>) -> Unit) {
        val call = getRetrofitDdragon().create(RiotAPI::class.java)
            .getChampions("champion.json")
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

    /**
     * Get the top 5 champions of summoner ID in descendant mastery order.
     */
    fun getTop5MasteryChampions(activity: Activity, summonerId: String, callBack: (List<ChampionMastery>) -> Unit) {
        val call = getRetrofitRiot().create(RiotAPI::class.java)
            .getTopMasteryChampions("champion-mastery/v4/champion-masteries/by-summoner/$summonerId?api_key=$apiKey")
        activity.runOnUiThread {
            val response = call!!.execute().body() ?: return@runOnUiThread
            callBack(response.toList().subList(0,10) as List<ChampionMastery>)
        }
    }

    /**
     * Get the weekly free champions rotation.
     */
    suspend fun getFreeChampionIds(activity: Activity, callBack: (List<String>) -> Unit) {
        val call = getRetrofitRiot().create(RiotAPI::class.java)
            .getWeeklyChampionRotation("platform/v3/champion-rotations?api_key=$apiKey")
        val callChampList = call.body()
        activity.runOnUiThread {
            if (call.isSuccessful) {
                callBack(callChampList?.freeChampionIds ?: emptyList())
            } else {
                Log.d("Error Retrofit", "Free Champion Rotation error")
            }
        }
    }

    /**
     * Get the weekly free champions rotation.
     */
    suspend fun getSummonerId(activity: Activity, summonerName: String, callBack: (String) -> Unit) {
        val call = getRetrofitRiot().create(RiotAPI::class.java)
            .getSummonerId("summoner/v4/summoners/by-name/$summonerName?api_key=$apiKey")
        val response = call.body()
        activity.runOnUiThread {
            if (call.isSuccessful) {
                callBack(response!!.summonerId)
            } else {
                Log.d("Error Retrofit", "Get summoner ID error")
                callBack("")
            }
        }
    }

}