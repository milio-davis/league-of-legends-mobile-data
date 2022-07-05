package com.example.leagueoflegendsapk.database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "champions")
data class DBChampionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "internalName") var internalName: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "imageUrl") var imageUrl: String
)
