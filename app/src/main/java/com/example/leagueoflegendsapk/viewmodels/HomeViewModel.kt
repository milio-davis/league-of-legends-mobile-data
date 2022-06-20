package com.example.leagueoflegendsapk.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leagueoflegendsapk.entities.Champion


class HomeViewModel : ViewModel() {

    var championList = MutableLiveData<MutableList<Champion>>()

    var champs : MutableList<Champion> = mutableListOf()

    init {
        champs.add(Champion("asd"))
        champs.add(Champion("DSA"))
        champs.add(Champion("asd"))
        championList.apply {
            value = champs
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getSummonner(summonersName: String?) {
        val url = "https://la2.api.riotgames.com/lol/summoner/v4/summoners/by-name/${summonersName}?api_key=RGAPI-1030e9e3-1642-4038-a276-2449388fe9eb"
    }

}