package com.inovagab.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.inovagab.R
import com.inovagab.databinding.ItemStrategyBinding
import com.inovagab.domain.model.Strategy

class StrategyAdapter(
    private val items: List<Strategy>,
    private val showActions: Boolean = false,
    private val onEdit: ((Strategy) -> Unit)? = null,
    private val onDelete: ((Strategy) -> Unit)? = null
) : RecyclerView.Adapter<StrategyAdapter.StrategyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StrategyViewHolder {
        val binding = ItemStrategyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StrategyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StrategyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class StrategyViewHolder(
        private val binding: ItemStrategyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(strategy: Strategy) {
            binding.tvStrategyTitle.text = strategy.titulo
            binding.tvStrategyDescription.text = strategy.descricao
            val iconRes = when (strategy.icone) {
                "coins" -> R.drawable.ic_coins
                "trending" -> R.drawable.ic_trending
                "shield" -> R.drawable.ic_shield
                else -> R.drawable.ic_settings
            }
            binding.ivStrategyIcon.setImageResource(iconRes)
            binding.ivStrategyIcon.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.primary)
            )
            binding.layoutActions.visibility = if (showActions) android.view.View.VISIBLE else android.view.View.GONE
            binding.btnEdit.setOnClickListener { onEdit?.invoke(strategy) }
            binding.btnDelete.setOnClickListener { onDelete?.invoke(strategy) }
        }
    }
}
