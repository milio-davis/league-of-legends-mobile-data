package com.example.leagueoflegendsapk.entities

import com.google.gson.annotations.SerializedName

data class ChampionMastery(
    @field:SerializedName("championLevel") val championLevel: Int,
    @field:SerializedName("championPoints") val championPoints: Long,
    @field:SerializedName("championId") val id: Int
    ) {

}