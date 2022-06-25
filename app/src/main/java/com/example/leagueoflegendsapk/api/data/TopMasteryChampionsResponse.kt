package com.example.leagueoflegendsapk.api.data

import com.example.leagueoflegendsapk.entities.Champion
import com.google.gson.annotations.SerializedName

data class TopMasteryChampionsResponse(
    @field:SerializedName("data") val championsList: List<Champion>
)