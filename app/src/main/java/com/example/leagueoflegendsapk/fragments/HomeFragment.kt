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
import org.w3c.dom.Text

class HomeFragment : Fragment() {

    lateinit var v: View

    lateinit var recContactos : RecyclerView

    var championList : MutableList<Champion> = ArrayList()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var championRotationAdapter: ChampionRotationAdapter

    private lateinit var sharedPref: SharedPreferences

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v =  inflater.inflate(R.layout.fragment_home, container, false)

        recContactos = v.findViewById(R.id.recyclerRotacionSemanal)

        sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)
        v.findViewById<TextView>(R.id.txtSummonersNameHome).text = sharedPref.getString("summonersName", "")

        return v
    }

    override fun onStart() {
        super.onStart()

        //Creo la Lista Dinamica
        for (i in 1..5) {
            championList.add(Champion("Pedro.$i"))
            championList.add(Champion("Rodolfo.$i"))
            championList.add(Champion("Emilio.$i"))
        }

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

}