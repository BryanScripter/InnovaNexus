package com.inovagab.presentation.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.inovagab.databinding.ItemMenuCardBinding
import com.inovagab.presentation.components.MenuItem

class MenuAdapter(
    private val items: List<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class MenuViewHolder(
        private val binding: ItemMenuCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MenuItem) {
            val ctx = binding.root.context
            binding.tvMenuTitle.text = item.title
            binding.tvMenuDescription.text = item.description
            binding.ivMenuIcon.setImageResource(item.iconRes)
            binding.ivMenuIcon.setColorFilter(ContextCompat.getColor(ctx, item.iconTintRes))

            val bg = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = ctx.resources.getDimension(com.inovagab.R.dimen.menu_icon_radius)
                setColor(ContextCompat.getColor(ctx, item.iconBackgroundColorRes))
            }
            binding.iconContainer.background = bg

            binding.badgeCount.isVisible = item.badgeCount != null
            item.badgeCount?.let { binding.badgeCount.text = it.toString() }
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}
