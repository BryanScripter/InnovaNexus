package com.inovagab.data.remote.dto

data class IdeaDTO(
    val id: String,
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val impacto: String,
    val status: String,
    val autor: String,
    val autorId: String,
    val dataEnvio: String,
    val score: Int
)
