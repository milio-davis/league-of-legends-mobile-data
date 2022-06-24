package com.example.leagueoflegendsapk.entities

import com.google.gson.annotations.SerializedName

data class Champion(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("id") val internalName: String,
    @field:SerializedName("key") val id: Int,
    @field:SerializedName("title") val title: String
    ) {

    private val imageBaseUrl = "https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/"
    var imageUrl = "${this.imageBaseUrl}${this.internalName}.png"

}