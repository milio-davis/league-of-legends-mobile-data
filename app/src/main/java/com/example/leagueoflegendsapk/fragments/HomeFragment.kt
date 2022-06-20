package com.example.leagueoflegendsapk.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.example.leagueoflegendsapk.adapters.ChampionRotationAdapter
import com.example.leagueoflegendsapk.entities.Champion
import com.google.android.material.snackbar.Snackbar
import com.example.leagueoflegendsapk.api.interfaces.RiotAPI
import com.example.leagueoflegendsapk.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    lateinit var v: View

    lateinit var recContactos : RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var championRotationAdapter: ChampionRotationAdapter

    private val championsList = mutableListOf<Champion>()

    private val champions = mutableListOf<Champion>()

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

        //Configuración Obligatoria

        recContactos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)

        recContactos.layoutManager = linearLayoutManager

        championRotationAdapter = ChampionRotationAdapter(championsList) { x ->
            onItemClick(x)
        }

        recContactos.adapter = championRotationAdapter

        s()
    }

    fun onItemClick ( position : Int ) : Boolean{
        Snackbar.make(v,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun getRetrofitRiot(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://la2.api.riotgames.com/lol/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRetrofitDdragon(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://ddragon.leagueoflegends.com/cdn/12.11.1/data/es_MX/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun s(){
        CoroutineScope(Dispatchers.IO).launch {
            val call1 = async { getFreeChampionIds() }

            val ids = call1.await()
            Log.d("RETRO", ids.toString())
        }
    }

    private suspend fun getFreeChampionIds() {
        val call = getRetrofitRiot().create(RiotAPI::class.java).getWeeklyChampionRotation("platform/v3/champion-rotations?api_key=$apiKey")
        val callChampList = call.body()
        requireActivity().runOnUiThread {
            if (call.isSuccessful) {
                val championIds = callChampList?.freeChampionIds ?: emptyList()
                Log.d("RETRO", championIds.toString())
                championsList.clear()
                //championsList.addAll(championIds)
                //championRotationAdapter.notifyDataSetChanged()
            } else {
                Log.d("RETRO", "ERROR")
            }
        }
    }

}