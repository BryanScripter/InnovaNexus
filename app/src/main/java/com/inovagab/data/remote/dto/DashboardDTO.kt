package com.inovagab.data.remote.dto

data class DashboardDTO(
    val roiTotal: Double,
    val economiaGerada: Double,
    val ideiasAprovadas: Int,
    val projetosAtivos: Int,
    val produtividadePercent: Int
)
