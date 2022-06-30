package com.example.leagueoflegendsapk.api.data

import com.google.gson.annotations.SerializedName

data class SummonerDataResponse(
    @field:SerializedName("id") val summonerId: String
)