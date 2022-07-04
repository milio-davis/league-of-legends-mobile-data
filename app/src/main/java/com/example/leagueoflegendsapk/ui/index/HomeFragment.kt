package com.example.leagueoflegendsapk.ui.index

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.adapters.ChampionRotationAdapter
import com.example.leagueoflegendsapk.adapters.LaneTabsAdapter
import com.example.leagueoflegendsapk.adapters.TopMasteryChampionsAdapter
import com.example.leagueoflegendsapk.api.RetrofitManager
import com.example.leagueoflegendsapk.database.ChampionDAO
import com.example.leagueoflegendsapk.database.DB
import com.example.leagueoflegendsapk.database.DBChampionEntity
import com.example.leagueoflegendsapk.databinding.FragmentIndexBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var summonerName: String
    private lateinit var summonerId: String

    private lateinit var binding: FragmentIndexBinding

    private lateinit var recyclerWeeklyRotation : RecyclerView
    private lateinit var championRotationAdapter: ChampionRotationAdapter

    private lateinit var recyclerTopMasteryChampions : RecyclerView
    private lateinit var topMasteryChampionsAdapter: TopMasteryChampionsAdapter

    private lateinit var viewPager: ViewPager2

    private lateinit var db: DB
    private lateinit var championDAO: ChampionDAO

    private val retrofitManager = RetrofitManager()

    private var weeklyRotationChampionsList = mutableListOf<DBChampionEntity>()
    private var topMasteryChampionsList = mutableListOf<DBChampionEntity>()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentIndexBinding.inflate(layoutInflater)

        db = DB.getAppDataBase(requireContext())!!
        championDAO = db.getChampionDAO()

        recyclerWeeklyRotation = binding.recyclerRotacionSemanal
        recyclerTopMasteryChampions = binding.recyclerTopMasteryChampions

        sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)
        summonerName = sharedPref.getString("summonersName", "")!!

        binding.txtSummonersNameHome.text = summonerName

        requestSummonerIdAndBestChampions(requireActivity(), summonerName)

        setLanesViewpager()

        fetchWeeklyChampionRotation(requireActivity())

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //Configuración Obligatoria

        // Recycler rotacion semanal
        recyclerWeeklyRotation.setHasFixedSize(true)
        recyclerWeeklyRotation.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        championRotationAdapter = ChampionRotationAdapter(weeklyRotationChampionsList) { x ->
            onItemClick(x)
        }
        recyclerWeeklyRotation.adapter = championRotationAdapter

        // Recycler top champions
        recyclerTopMasteryChampions.setHasFixedSize(true)
        recyclerTopMasteryChampions.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        topMasteryChampionsAdapter = TopMasteryChampionsAdapter(topMasteryChampionsList) { x ->
            onItemClick(x)
        }
        recyclerTopMasteryChampions.adapter = topMasteryChampionsAdapter
    }

    private fun onItemClick (position : Int ) : Boolean{
        Snackbar.make(binding.root,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun requestSummonerIdAndBestChampions(activity: Activity, summonerName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            async { retrofitManager.getSummonerId(activity, summonerName) {
                if (it == "") binding.txtSummonersNameHome.text = "Summoner Name no existe"
                else {
                    summonerId = it
                    sharedPref.edit().putString("summonerId", summonerId).apply()
                    fetchBestChampions(requireActivity())
                }
            }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchBestChampions(activity: Activity) {
        CoroutineScope(Dispatchers.IO).launch {
            async { retrofitManager.getTop5MasteryChampions(activity, summonerId) { champions ->
                champions.forEach {
                    topMasteryChampionsList.add(championDAO.loadChampionById(it.id)!!)
                }
                topMasteryChampionsAdapter.notifyDataSetChanged()
            }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchWeeklyChampionRotation(activity: Activity) {
        CoroutineScope(Dispatchers.IO).launch {
            async { retrofitManager.getFreeChampionIds(activity) { freeChampionsIds ->
                freeChampionsIds.forEach {
                    weeklyRotationChampionsList.add(championDAO.loadChampionById(it.toInt())!!)
                }
                championRotationAdapter.notifyDataSetChanged()
            }
            }
        }
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
            }
        }.attach()
    }

}