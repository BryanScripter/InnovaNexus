package com.inovagab.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val nome: String,
    val descricao: String,
    val status: String,
    val progresso: Int,
    val investimento: Double,
    val retornoEsperado: Double,
    val prazo: String,
    val ideiaOrigem: String?
)
