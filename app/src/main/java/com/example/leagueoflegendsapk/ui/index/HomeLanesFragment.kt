package com.example.leagueoflegendsapk.ui.index

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.leagueoflegendsapk.R
import com.example.leagueoflegendsapk.api.RetrofitManager
import com.example.leagueoflegendsapk.databinding.FragmentHomeLanesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeLanesFragment : Fragment() {

    private lateinit var binding: FragmentHomeLanesBinding

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeLanesBinding.inflate(layoutInflater)

        sharedPref = requireContext().getSharedPreferences("lolSharedPreferences", Context.MODE_PRIVATE)

        fetchAndSetData(requireArguments().getInt("position",-1),
            sharedPref.getString("summonerId", "")!!)

        return binding.root
    }

    private fun fetchAndSetData(position: Int, summonerId: String) {
        if (position == (-1)) return
        CoroutineScope(Dispatchers.IO).launch {
            async { RetrofitManager().getRanks(requireActivity(), summonerId) { ranks ->
                //Unranked
                if (ranks.size == 1) {
                    binding.txtQueueType.text = "Unranked"
                    return@getRanks
                }
                binding.txtQueueType.text = ranks[position]!!.queueType
                binding.txtRank.text = "${resources.getString(R.string.title_rank)}: ${ranks[position]!!.rank}"
                val tier = ranks[position]!!.tier
                binding.txtTier.text = "${resources.getString(R.string.title_tier)}: $tier"
                binding.txtLeaguePoints.text = "${resources.getString(R.string.title_leaguePoints)}: ${ranks[position]!!.leaguePoints}"
                binding.txtWins.text = "${resources.getString(R.string.title_wins)}: ${ranks[position]!!.wins}"
                binding.txtLosses.text = "${resources.getString(R.string.title_losses)}: ${ranks[position]!!.losses}"
                setImageRank(tier)
            }
            }
        }
    }

    private fun setImageRank(tier: String) {
        var image: Int = R.mipmap.rank_bronze_foreground
        when (tier) {
            "IRON" -> image = R.mipmap.rank_iron_foreground
            "BRONZE" -> image = R.mipmap.rank_bronze_foreground
            "SILVER" -> image = R.mipmap.rank_silver_foreground
            "GOLD" -> image = R.mipmap.rank_gold_foreground
            "PLATINUM" -> image = R.mipmap.rank_platinum_foreground
            "DIAMOND" -> image = R.mipmap.rank_diamond_foreground
            "MASTER" -> image = R.mipmap.rank_master_foreground
            "GRANDMASTER" -> image = R.mipmap.rank_grandmaster_foreground
            "CHALLENGER" -> image = R.mipmap.rank_challenger_foreground
        }
        binding.imgRank.setBackgroundResource(image)
    }
}