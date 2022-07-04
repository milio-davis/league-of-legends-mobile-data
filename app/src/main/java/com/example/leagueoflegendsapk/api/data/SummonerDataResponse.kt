package com.example.leagueoflegendsapk.api.data

import com.google.gson.annotations.SerializedName

data class SummonerDataResponse(
    @field:SerializedName("id") val summonerId: String,
    @field:SerializedName("profileIconId") val profileIconId: Int,
    @field:SerializedName("summonerLevel") val summonerLevel: Int
)