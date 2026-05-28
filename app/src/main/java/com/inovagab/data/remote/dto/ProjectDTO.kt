package com.inovagab.data.remote.dto

data class ProjectDTO(
    val id: String,
    val nome: String,
    val descricao: String,
    val status: String,
    val progresso: Int,
    val investimento: Double,
    val retornoEsperado: Double,
    val prazo: String,
    val ideiaOrigem: String? = null
)
