package com.inovagab.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.inovagab.R
import com.inovagab.databinding.ItemIdeaBinding
import com.inovagab.domain.model.Idea
import com.inovagab.utils.UiHelpers

class IdeaAdapter(
    private val items: List<Idea>,
    private val showManagerMeta: Boolean = false,
    private val onItemClick: ((Idea) -> Unit)? = null
) : RecyclerView.Adapter<IdeaAdapter.IdeaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeaViewHolder {
        val binding = ItemIdeaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IdeaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IdeaViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class IdeaViewHolder(
        private val binding: ItemIdeaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(idea: Idea) {
            val ctx = binding.root.context
            binding.tvIdeaTitle.text = idea.titulo
            UiHelpers.applyIdeaStatusChip(ctx, binding.chipStatus, idea.status)
            binding.tvCategory.text = idea.categoria
            binding.tvImpact.text = ctx.getString(com.inovagab.R.string.impact_label, idea.impacto)
            binding.tvAuthor.isVisible = showManagerMeta
            if (showManagerMeta) {
                binding.tvAuthor.isVisible = true
                binding.tvAuthor.text = idea.autor
                binding.chipScore.isVisible = true
                binding.chipScore.text = "${idea.score} pts"
                binding.chipScore.setTextColor(ContextCompat.getColor(ctx, R.color.primary))
            } else {
                binding.chipScore.isVisible = false
            }
            binding.root.setOnClickListener { onItemClick?.invoke(idea) }
        }
    }
}
