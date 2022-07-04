package com.example.leagueoflegendsapk.api.data

import com.google.gson.annotations.SerializedName

data class RankResponse(
    @field:SerializedName("queueType") val queueType: String,
    @field:SerializedName("tier") val tier: String,
    @field:SerializedName("rank") val rank: String,
    @field:SerializedName("leaguePoints") val leaguePoints: Int,
    @field:SerializedName("wins") val wins: Int,
    @field:SerializedName("losses") val losses: Int
    )