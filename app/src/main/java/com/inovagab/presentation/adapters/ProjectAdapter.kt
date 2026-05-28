package com.inovagab.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.inovagab.databinding.ItemProjectBinding
import com.inovagab.domain.model.Project
import com.inovagab.utils.UiHelpers

class ProjectAdapter(
    private val items: List<Project>,
    private val leadershipMode: Boolean = false,
    private val showEditButton: Boolean = false,
    private val onEditClick: ((Project) -> Unit)? = null,
    private val onItemClick: ((Project) -> Unit)? = null
) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ProjectViewHolder(
        private val binding: ItemProjectBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project) {
            val ctx = binding.root.context
            binding.tvProjectTitle.text = project.nome
            UiHelpers.applyProjectStatusChip(ctx, binding.chipStatus, project.status)
            binding.progressBar.progress = project.progresso
            binding.tvProjectDescription.isVisible = leadershipMode
            if (leadershipMode) {
                binding.tvProjectDescription.text = project.descricao
                val roi = ((project.retornoEsperado - project.investimento) / project.investimento * 100).toInt()
                binding.tvInvestment.text = "${UiHelpers.formatCurrency(project.investimento)} • ROI ${roi}%"
            } else {
                binding.tvInvestment.text = UiHelpers.formatCurrency(project.investimento)
            }
            binding.btnEdit.isVisible = showEditButton
            binding.btnEdit.setOnClickListener { onEditClick?.invoke(project) }
            binding.root.setOnClickListener { onItemClick?.invoke(project) }
        }
    }
}
