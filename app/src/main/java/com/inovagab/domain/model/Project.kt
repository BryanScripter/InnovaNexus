package com.inovagab.domain.model

data class Project(
    val id: String,
    val nome: String,
    val descricao: String,
    val status: ProjectStatus,
    val progresso: Int,
    val investimento: Double,
    val retornoEsperado: Double,
    val prazo: String,
    val ideiaOrigem: String? = null
)

enum class ProjectStatus(val value: String, val label: String) {
    PLANEJAMENTO("planejamento", "Planejamento"),
    EXECUCAO("execucao", "Em Execução"),
    CONCLUIDO("concluido", "Concluído"),
    PAUSADO("pausado", "Pausado");

    companion object {
        fun from(value: String): ProjectStatus = entries.first { it.value == value }
    }
}
