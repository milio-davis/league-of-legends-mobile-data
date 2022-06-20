package com.example.leagueoflegendsapk.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.example.leagueoflegendsapk.adapters.ChampionRotationAdapter
import com.example.leagueoflegendsapk.entities.Champion
import com.google.android.material.snackbar.Snackbar
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.api.RiotService
import com.example.leagueoflegendsapk.api.data.ChampionResponse
import com.example.leagueoflegendsapk.api.data.RiotResponse
import com.example.leagueoflegendsapk.api.interfaces.RiotAPI
import com.example.leagueoflegendsapk.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    lateinit var v: View

    lateinit var recContactos : RecyclerView

    var championList : MutableList<Champion> = ArrayList()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var championRotationAdapter: ChampionRotationAdapter

    private lateinit var sharedPref: SharedPreferences

    private lateinit var binding: FragmentHomeBinding

    val apiKey = "RGAPI-68c755e8-7e2a-469e-9711-de8ea8797bcc"

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        //v =  inflater.inflate(R.layout.fragment_home, container, false)

        recContactos = binding.recyclerRotacionSemanal

        sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)
        binding.txtSummonersNameHome.text = sharedPref.getString("summonersName", "")

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        championList.add(Champion("Pedro","https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Caitlyn.png"))
        championList.add(Champion("Rodolfo","https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Aatrox.png"))
        championList.add(Champion("Emilio","https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Rammus.png"))


        //Configuración Obligatoria

        recContactos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)

        recContactos.layoutManager = linearLayoutManager

        championRotationAdapter = ChampionRotationAdapter(championList) { x ->
            onItemClick(x)
        }

        recContactos.adapter = championRotationAdapter

    }

    fun onItemClick ( position : Int ) : Boolean{
        Snackbar.make(v,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://la2.api.riotgames.com/lol/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun s(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(RiotAPI::class.java).getWeeklyChampionRotation("platform/v3/champion-rotations?api_key=$apiKey")
            val puppies = call.body()
            if (call.isSuccessful) {
                // show recycler
            } else {
                // error
            }
        }
    }

}