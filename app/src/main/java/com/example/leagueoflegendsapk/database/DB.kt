package com.example.leagueoflegendsapk.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DBChampionEntity::class], version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {

    abstract fun getChampionDAO():ChampionDAO

    companion object {

        var INSTANCE: DB? = null

        fun getAppDataBase(context: Context): DB? {
            if (INSTANCE == null) {
                synchronized(DB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DB::class.java,
                        "myDB"
                    ).allowMainThreadQueries().build() // No es lo mas recomendable que se ejecute en el mainthread
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}