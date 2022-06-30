package com.example.leagueoflegendsapk.api.interfaces

import com.example.leagueoflegendsapk.api.data.ChampionsResponse
import com.example.leagueoflegendsapk.api.data.SummonerDataResponse
import com.example.leagueoflegendsapk.api.data.WeeklyChampionRotationResponse
import com.example.leagueoflegendsapk.entities.ChampionMastery
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RiotAPI {
    @GET
    suspend fun getWeeklyChampionRotation(@Url url:String): Response<WeeklyChampionRotationResponse>

    @GET
    suspend fun getChampions(@Url url:String): Response<ChampionsResponse>

    @GET
    fun getTopMasteryChampions(@Url url:String): Call<List<ChampionMastery?>?>?

    @GET
    suspend fun getSummonerId(@Url url:String): Response<SummonerDataResponse>
}