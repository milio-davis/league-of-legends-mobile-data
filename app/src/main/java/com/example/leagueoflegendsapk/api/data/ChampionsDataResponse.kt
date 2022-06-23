package com.example.leagueoflegendsapk.api.data

import com.example.leagueoflegendsapk.entities.Champion
import com.google.gson.annotations.SerializedName

data class ChampionsDataResponse(
    @field:SerializedName("Aatrox") val champion: Champion
)