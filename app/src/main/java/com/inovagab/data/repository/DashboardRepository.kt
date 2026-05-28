package com.inovagab.data.repository

import com.inovagab.data.remote.api.ApiService
import com.inovagab.domain.model.DashboardData
import com.inovagab.domain.model.IdeaStatus
import com.inovagab.domain.model.ProjectStatus
import kotlinx.coroutines.flow.first
import com.inovagab.data.repository.IdeaRepository
import com.inovagab.data.repository.ProjectRepository

class DashboardRepository(
    private val apiService: ApiService,
    private val ideaRepository: IdeaRepository,
    private val projectRepository: ProjectRepository
) {
    suspend fun getDashboard(): DashboardData {
        return try {
            val dto = apiService.getDashboard()
            DashboardData(
                roiTotal = dto.roiTotal,
                economiaGerada = dto.economiaGerada,
                ideiasAprovadas = dto.ideiasAprovadas,
                projetosAtivos = dto.projetosAtivos,
                produtividadePercent = dto.produtividadePercent
            )
        } catch (_: Exception) {
            val ideas = ideaRepository.observeIdeas().first()
            val projects = projectRepository.observeProjects().first()
            val totalInvestimento = projects.sumOf { it.investimento }
            val totalRetorno = projects.sumOf { it.retornoEsperado }
            val roi = if (totalInvestimento > 0) {
                ((totalRetorno - totalInvestimento) / totalInvestimento) * 100
            } else 0.0
            DashboardData(
                roiTotal = roi,
                economiaGerada = totalRetorno - totalInvestimento,
                ideiasAprovadas = ideas.count { it.status == IdeaStatus.APROVADA },
                projetosAtivos = projects.count { it.status == ProjectStatus.EXECUCAO },
                produtividadePercent = 23
            )
        }
    }
}
