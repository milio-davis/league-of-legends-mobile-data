package com.example.leagueoflegendsapk.api.data

import com.example.leagueoflegendsapk.entities.Champion
import com.google.gson.annotations.SerializedName

data class WeeklyChampionRotationResponse(
    @field:SerializedName("freeChampionIds") val freeChampionIds: List<String>
)