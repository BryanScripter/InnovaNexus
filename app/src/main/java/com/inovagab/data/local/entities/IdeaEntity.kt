package com.inovagab.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ideas")
data class IdeaEntity(
    @PrimaryKey val id: String,
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
