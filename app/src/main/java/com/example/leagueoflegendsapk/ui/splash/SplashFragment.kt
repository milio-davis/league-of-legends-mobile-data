package com.example.leagueoflegendsapk.ui.splash

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.api.RetrofitManager
import com.example.leagueoflegendsapk.database.ChampionDAO
import com.example.leagueoflegendsapk.database.DB
import com.example.leagueoflegendsapk.database.DBChampionEntity
import com.example.leagueoflegendsapk.entities.Champion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var db: DB? = null
    private var championDAO: ChampionDAO? = null

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = DB.getAppDataBase(requireContext())
        championDAO = db?.getChampionDAO()

        fetchChampionsRequest(requireActivity())
        advanceActivityByTimeout(3000)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    /**
     * Move APP  to MainActivity after timeout
     */
    private fun advanceActivityByTimeout(splashTimeout: Long) {
        Handler().postDelayed({
            var sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)
            if (sharedPref.getString("summonersName", "") == "")
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            else {
                findNavController().navigate(R.id.action_splashFragment_to_mainActivity)
                requireActivity().finish()
            }
        }, splashTimeout)
    }

    private fun fetchChampionsRequest(activity: Activity) {
        val retrofitManager = RetrofitManager()
        CoroutineScope(Dispatchers.IO).launch {
            async { retrofitManager.getChampions(activity) { champions -> setChampionsInDB(champions)} }
        }
    }

    private fun setChampionsInDB(champions: List<Champion>) {
        champions.forEach {
            championDAO?.insertChampion(
                DBChampionEntity(it.id, it.name,
                it.internalName, it.title, it.imageUrl)
            )
        }
    }

}