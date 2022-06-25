package com.example.leagueoflegendsapk.database

import androidx.room.*

@Dao
interface ChampionDAO {

    @Query("SELECT * FROM champions ORDER BY name ASC")
    fun getAll() : List<DBChampionEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChampion(champion: DBChampionEntity?)

    @Update
    fun updateChampion(champion: DBChampionEntity?)

    @Delete
    fun deleteChampion(champion: DBChampionEntity?)

    @Query("SELECT * FROM champions ORDER BY id")
    fun loadAllChampions(): MutableList<DBChampionEntity?>?

    @Query("SELECT * FROM champions WHERE id = :id")
    fun loadChampionById(id: Int): DBChampionEntity?
}