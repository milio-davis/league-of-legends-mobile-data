package com.example.leagueoflegendsapk.ui.index

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
import com.example.leagueoflegendsapk.databinding.FragmentIndexBinding
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

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentIndexBinding

    private lateinit var recyclerRotacionSemanal : RecyclerView
    private lateinit var championRotationAdapter: ChampionRotationAdapter

    private lateinit var recyclerTopMasteryChampions : RecyclerView
    private lateinit var topMasteryChampionsAdapter: TopMasteryChampionsAdapter

    private val championsList = mutableListOf<Champion>()

    private lateinit var viewPager: ViewPager2

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentIndexBinding.inflate(layoutInflater)

        recyclerRotacionSemanal = binding.recyclerRotacionSemanal
        recyclerTopMasteryChampions = binding.recyclerTopMasteryChampions

        sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)
        binding.txtSummonersNameHome.text = sharedPref.getString("summonersName", "")

        setLanesViewpager()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

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
    }

    private fun onItemClick (position : Int ) : Boolean{
        Snackbar.make(binding.root,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun setLanesViewpager() {
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
    }

}