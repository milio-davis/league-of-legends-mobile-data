package com.example.leagueoflegendsapk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.database.DBChampionEntity
import com.example.leagueoflegendsapk.databinding.ItemChampionRotationBinding
import com.example.leagueoflegendsapk.entities.Champion
import com.squareup.picasso.Picasso

class TopMasteryChampionsAdapter(
    private var championsList: List<DBChampionEntity>,
    val onItemClick: (Int) -> Boolean
) : RecyclerView.Adapter<TopMasteryChampionsAdapter.ChampionHolder>() {

    override fun getItemCount(): Int {
        return championsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_champion_rotation,parent,false)
        return (ChampionHolder(view))
    }

    override fun onBindViewHolder(holder: ChampionHolder, position: Int) {

        holder.setImage(championsList[position].imageUrl)

        holder.getCardLayout().setOnClickListener{
            onItemClick(position)
        }
    }

    class ChampionHolder (v: View) : RecyclerView.ViewHolder(v) {

        private val binding = ItemChampionRotationBinding.bind(v)

        fun setImage(path: String) {
            Picasso.get().load(path).into(binding.imageView)
        }

        fun getCardLayout (): CardView {
            return binding.cardPackageItem
        }

    }
}