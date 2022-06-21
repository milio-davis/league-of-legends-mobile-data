package com.example.leagueoflegendsapk.api.data

import com.example.leagueoflegendsapk.entities.Champion
import com.google.gson.annotations.SerializedName

data class ChampionsResponse(
    @field:SerializedName("data") val championsObj: String
    // {data: {Aatrox: {id, name}, Ahri: {...} }
)