package com.example.leagueoflegendsapk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.entities.Champion

class ChampionRotationAdapter(
    private var contactsList: MutableList<Champion>,
    val onItemClick: (Int) -> Boolean
) : RecyclerView.Adapter<ChampionRotationAdapter.ChampionHolder>() {

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_champion_rotation,parent,false)
        return (ChampionHolder(view))
    }

    override fun onBindViewHolder(holder: ChampionHolder, position: Int) {

        holder.setName(contactsList[position].nombre)

        holder.getCardLayout().setOnClickListener{
            onItemClick(position)
        }
    }

    fun setData(newData: ArrayList<Champion>) {
        this.contactsList = newData
        this.notifyDataSetChanged()
    }

    class ChampionHolder (v: View) : RecyclerView.ViewHolder(v) {

        private var view: View

        init {
            this.view = v
        }

        fun setName(name: String) {
            val txt: TextView = view.findViewById(R.id.txt_name_item)
            txt.text = name
        }

        fun getCardLayout (): CardView {
            return view.findViewById(R.id.card_package_item)
        }

    }
}