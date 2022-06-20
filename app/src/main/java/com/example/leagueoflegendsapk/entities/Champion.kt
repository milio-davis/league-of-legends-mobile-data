package com.example.leagueoflegendsapk.entities

import com.google.gson.annotations.SerializedName

data class Champion(
    @field:SerializedName("nombre") val nombre: String,
    @field:SerializedName("imageUrl") val imageUrl: String
    ) {

}