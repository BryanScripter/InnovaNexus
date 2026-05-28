package com.inovagab.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inovagab.databinding.ItemRankingBinding
import com.inovagab.domain.model.RankingItem
import com.inovagab.utils.UiHelpers

class RankingAdapter(
    private val items: List<RankingItem>,
    private val startRank: Int = 1
) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bind(items[position], startRank + position)
    }

    override fun getItemCount(): Int = items.size

    class RankingViewHolder(
        private val binding: ItemRankingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RankingItem, rank: Int) {
            binding.tvRank.text = rank.toString()
            binding.tvName.text = item.nome
            binding.tvPoints.text = "${item.pontuacao} pts"
            binding.tvInitials.text = UiHelpers.singleInitial(item.nome)
        }
    }
}
