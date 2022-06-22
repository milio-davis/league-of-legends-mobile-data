package com.example.leagueoflegendsapk.api.interfaces

import com.example.leagueoflegendsapk.api.data.ChampionsResponse
import com.example.leagueoflegendsapk.api.data.TopMasteryChampionsResponse
import com.example.leagueoflegendsapk.api.data.WeeklyChampionRotationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RiotAPI {
    /*
    @GET("v1/public/characters")
    fun getCharacters(
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String = "ort",
    ): Call<RiotResponse<ChampionResponse?>?>?
     */

    @GET
    suspend fun getWeeklyChampionRotation(@Url url:String): Response<WeeklyChampionRotationResponse>

    @GET
    suspend fun getChampions(@Url url:String): Response<ChampionsResponse>

    @GET
    suspend fun getTopMasteryChampions(@Url url:String): Response<TopMasteryChampionsResponse>
}