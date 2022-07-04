package com.example.leagueoflegendsapk.ui.champions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leagueoflegendsapk.adapters.ChampionRotationAdapter
import com.example.leagueoflegendsapk.database.ChampionDAO
import com.example.leagueoflegendsapk.database.DB
import com.example.leagueoflegendsapk.database.DBChampionEntity
import com.example.leagueoflegendsapk.databinding.FragmentChampionsBinding
import com.google.android.material.snackbar.Snackbar

class ChampionsFragment : Fragment() {

    private var _binding: FragmentChampionsBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerRotacionSemanal : RecyclerView
    private lateinit var championRotationAdapter: ChampionRotationAdapter

    private lateinit var db: DB
    private lateinit var championDAO: ChampionDAO
    private lateinit var championsList: List<DBChampionEntity>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val championsViewModel =
                ViewModelProvider(this).get(ChampionsViewModel::class.java)

        _binding = FragmentChampionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        db = DB.getAppDataBase(requireContext())!!
        championDAO = db?.getChampionDAO()
        championsList = championDAO?.getAll()

        recyclerRotacionSemanal = binding.recyclerRotacionSemanal

        return root
    }

    override fun onStart() {
        super.onStart()

        recyclerRotacionSemanal.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        championRotationAdapter = ChampionRotationAdapter(championsList) { x ->
            onItemClick(x)
        }

        recyclerRotacionSemanal.adapter = championRotationAdapter

    }

    private fun onItemClick (position : Int ) : Boolean{
        Snackbar.make(binding.root,position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}