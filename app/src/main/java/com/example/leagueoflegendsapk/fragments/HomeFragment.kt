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
import androidx.viewpager2.widget.ViewPager2
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.adapters.ChampionRotationAdapter
import com.example.leagueoflegendsapk.adapters.LaneTabsAdapter
import com.example.leagueoflegendsapk.adapters.TopMasteryChampionsAdapter
import com.example.leagueoflegendsapk.entities.Champion
import com.google.android.material.snackbar.Snackbar
import com.example.leagueoflegendsapk.api.interfaces.RiotAPI
import com.example.leagueoflegendsapk.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    val apiKey = "RGAPI-f0abcc1c-6bbe-48db-b438-ebc17082fd41"
    val summonerId = "6nwa1pkSeo2yUWI0gIFiD2wHVw21C71NQ2NyhnxN7B_XyZA"

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentHomeBinding

    lateinit var recyclerRotacionSemanal : RecyclerView
    private lateinit var championRotationAdapter: ChampionRotationAdapter

    lateinit var recyclerTopMasteryChampions : RecyclerView
    private lateinit var topMasteryChampionsAdapter: TopMasteryChampionsAdapter

    private val championsList = mutableListOf<Champion>()
    private lateinit var freeChampionIds: List<String>

    private lateinit var viewPager: ViewPager2

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        recyclerRotacionSemanal = binding.recyclerRotacionSemanal
        recyclerTopMasteryChampions = binding.recyclerTopMasteryChampions

        sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)
        binding.txtSummonersNameHome.text = sharedPref.getString("summonersName", "")

        viewPager = binding.pagerHomeLanes
        viewPager.adapter = LaneTabsAdapter(this)
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.title_top)
                1 -> tab.text = resources.getString(R.string.title_jungle)
                2 -> tab.text = resources.getString(R.string.title_mid)
                3 -> tab.text = resources.getString(R.string.title_bot)
                4 -> tab.text = resources.getString(R.string.title_support)
                else -> { // Note the block
                    print("")
                }
            }
        }.attach()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // TODO: Set championsList from DB
        championsList.add(Champion("a", "https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Aatrox.png", 0))
        championsList.add(Champion("a", "https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Caitlyn.png", 1))
        championsList.add(Champion("a", "https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Rammus.png", 2))
        championsList.add(Champion("a", "https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Veigar.png", 3))
        championsList.add(Champion("a", "https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Zac.png", 4))
        championsList.add(Champion("a", "https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Xerath.png", 5))
        championsList.add(Champion("a", "https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Kayle.png", 6))
        championsList.add(Champion("a", "https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/Xayah.png", 7))

        //Configuración Obligatoria

        // Recycler rotacion semanal
        recyclerRotacionSemanal.setHasFixedSize(true)
        recyclerRotacionSemanal.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        championRotationAdapter = ChampionRotationAdapter(championsList) { x ->
            onItemClick(x)
        }
        recyclerRotacionSemanal.adapter = championRotationAdapter

        // Recycler top champions
        recyclerTopMasteryChampions.setHasFixedSize(true)
        recyclerTopMasteryChampions.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        topMasteryChampionsAdapter = TopMasteryChampionsAdapter(championsList) { x ->
            onItemClick(x)
        }
        recyclerTopMasteryChampions.adapter = topMasteryChampionsAdapter

        //championRotationAdapter.notifyDataSetChanged()

        launchCoroutines()
    }

    fun onItemClick ( position : Int ) : Boolean{
        Snackbar.make(binding.root,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun getRetrofitRiot(): Retrofit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://la2.api.riotgames.com/lol/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRetrofitDdragon(): Retrofit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://ddragon.leagueoflegends.com/cdn/12.11.1/data/es_MX/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun launchCoroutines(){
        CoroutineScope(Dispatchers.IO).launch {
            val call1 = async { getFreeChampionIds() }
            //val call2 = async { getChampions() }
            //val call3 = async { getTopMasteryChampions() }

            Log.d("RETRO TEST", "1")
        }
    }

    private suspend fun getTopMasteryChampions() {
        val call = getRetrofitRiot().create(RiotAPI::class.java).getTopMasteryChampions("champion-mastery/v4/champion-masteries/by-summoner/$summonerId?api_key=$apiKey")
        val champions = call.body()
        requireActivity().runOnUiThread {
            if (call.isSuccessful) {
                Log.d("RETRO TEST", champions.toString())
            } else {
                Log.d("Error Retrofit", "Champions error")
            }
        }
    }

    private suspend fun getChampions() {
        val call = getRetrofitDdragon().create(RiotAPI::class.java).getChampions("champion.json")
        val champions = call.body()
        requireActivity().runOnUiThread {
            if (call.isSuccessful) {
                Log.d("RETRO TEST", champions.toString())
            } else {
                Log.d("Error Retrofit", "Champions error")
            }
        }
    }

    private suspend fun getFreeChampionIds() {
        val call = getRetrofitRiot().create(RiotAPI::class.java).getWeeklyChampionRotation("platform/v3/champion-rotations?api_key=$apiKey")
        val callChampList = call.body()
        requireActivity().runOnUiThread {
            if (call.isSuccessful) {
                freeChampionIds = callChampList?.freeChampionIds ?: emptyList()
                Log.d("RETRO TEST", freeChampionIds.toString())
            } else {
                Log.d("Error Retrofit", "Free Champion Rotation error")
            }
        }
    }

}