package com.example.leagueoflegendsapk.ui.champions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leagueoflegendsapk.adapters.ChampionRotationAdapter
import com.example.leagueoflegendsapk.database.ChampionDAO
import com.example.leagueoflegendsapk.database.DB
import com.example.leagueoflegendsapk.database.DBChampionEntity
import com.example.leagueoflegendsapk.databinding.FragmentChampionsBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class ChampionsFragment : Fragment() {

    private var _binding: FragmentChampionsBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerRotacionSemanal : RecyclerView
    private lateinit var championRotationAdapter: ChampionRotationAdapter

    private lateinit var searchView: SearchView

    private lateinit var db: DB
    private lateinit var championDAO: ChampionDAO
    private lateinit var championsList: List<DBChampionEntity>
    private lateinit var tempChampionsList: MutableList<DBChampionEntity>

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
        championDAO = db.getChampionDAO()
        championsList = championDAO.getAll()

        tempChampionsList = championDAO.getAll().toMutableList()

        recyclerRotacionSemanal = binding.recyclerRotacionSemanal

        searchView = binding.searchView

        return root
    }

    override fun onStart() {
        super.onStart()

        recyclerRotacionSemanal.layoutManager = GridLayoutManager(context, 4, LinearLayoutManager.VERTICAL, false)
        championRotationAdapter = ChampionRotationAdapter(tempChampionsList) { x ->
            onItemClick(x)
        }

        recyclerRotacionSemanal.adapter = championRotationAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempChampionsList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    championsList.forEach {
                        if (it.name.lowercase(Locale.getDefault()).contains(searchText)) {
                            tempChampionsList.add(it)
                        }
                    }

                    recyclerRotacionSemanal.adapter!!.notifyDataSetChanged()
                } else {
                    tempChampionsList.clear()
                    tempChampionsList.addAll(championsList)
                    recyclerRotacionSemanal.adapter!!.notifyDataSetChanged()
                }


                return false
            }

        })

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