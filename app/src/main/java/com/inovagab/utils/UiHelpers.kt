package com.inovagab.utils

import android.content.Context
import com.google.android.material.chip.Chip
import com.inovagab.R
import com.inovagab.domain.model.IdeaStatus
import com.inovagab.domain.model.ProjectStatus
import androidx.core.content.ContextCompat
import java.util.Calendar

object UiHelpers {

    fun greeting(context: Context): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when {
            hour < 12 -> context.getString(R.string.good_morning)
            hour < 18 -> context.getString(R.string.good_afternoon)
            else -> context.getString(R.string.good_evening)
        }
    }

    fun applyIdeaStatusChip(context: Context, chip: Chip, status: IdeaStatus) {
        val (bg, text, label) = when (status) {
            IdeaStatus.EM_ANALISE -> Triple(R.color.warning, R.color.warning_foreground, status.label)
            IdeaStatus.APROVADA -> Triple(R.color.success, R.color.success_foreground, status.label)
            IdeaStatus.REJEITADA -> Triple(R.color.destructive, R.color.primary_foreground, status.label)
            IdeaStatus.EM_PROJETO -> Triple(R.color.info, R.color.info_foreground, status.label)
        }
        chip.text = label
        chip.chipStrokeWidth = 0f
        chip.setChipBackgroundColorResource(bg)
        chip.setTextColor(ContextCompat.getColor(context, text))
        chip.chipMinHeight = context.resources.getDimension(R.dimen.chip_min_height)
        chip.textSize = 11f
    }

    fun applyProjectStatusChip(context: Context, chip: Chip, status: ProjectStatus) {
        val (bg, text) = when (status) {
            ProjectStatus.PLANEJAMENTO -> R.color.warning to R.color.warning_foreground
            ProjectStatus.EXECUCAO -> R.color.info to R.color.info_foreground
            ProjectStatus.CONCLUIDO -> R.color.success to R.color.success_foreground
            ProjectStatus.PAUSADO -> R.color.muted to R.color.muted_foreground
        }
        chip.text = status.label
        chip.chipStrokeWidth = 0f
        chip.setChipBackgroundColorResource(bg)
        chip.setTextColor(ContextCompat.getColor(context, text))
        chip.chipMinHeight = context.resources.getDimension(R.dimen.chip_min_height)
        chip.textSize = 11f
    }

    fun formatCurrency(value: Double): String {
        return java.text.NumberFormat.getCurrencyInstance(java.util.Locale("pt", "BR")).apply {
            maximumFractionDigits = 0
        }.format(value)
    }

    fun firstName(fullName: String): String = fullName.split(" ").firstOrNull() ?: fullName

    fun initials(fullName: String): String =
        fullName.split(" ").mapNotNull { it.firstOrNull()?.uppercaseChar() }.take(2).joinToString("")

    fun singleInitial(name: String): String =
        name.firstOrNull()?.uppercaseChar()?.toString() ?: ""
}
