package com.example.leagueoflegendsapk.entities

import android.os.Parcel
import android.os.Parcelable

class Champion(nombre: String?) {
    var nombre: String = ""

    class Constants {
        companion object {
            /*
            val cursoA = "A"
             */
        }
    }

    init {
        this.nombre = nombre!!
    }


}