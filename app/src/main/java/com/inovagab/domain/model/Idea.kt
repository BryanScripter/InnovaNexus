package com.inovagab.domain.model

data class Idea(
    val id: String,
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val impacto: String,
    val status: IdeaStatus,
    val autor: String,
    val autorId: String,
    val dataEnvio: String,
    val score: Int
)

enum class IdeaStatus(val value: String, val label: String) {
    EM_ANALISE("em_analise", "Em análise"),
    APROVADA("aprovada", "Aprovada"),
    REJEITADA("rejeitada", "Rejeitada"),
    EM_PROJETO("em_projeto", "Em projeto");

    companion object {
        fun from(value: String): IdeaStatus = entries.first { it.value == value }
    }
}
