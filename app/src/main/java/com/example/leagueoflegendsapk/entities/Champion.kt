package com.example.leagueoflegendsapk.entities

import com.google.gson.annotations.SerializedName

data class Champion(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("imageUrl") val imageUrl: String,
    @field:SerializedName("key") val key: String
    ) {

}