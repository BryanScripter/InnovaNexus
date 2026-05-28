package com.inovagab.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "strategies")
data class StrategyEntity(
    @PrimaryKey val id: String,
    val titulo: String,
    val descricao: String,
    val icone: String
)
