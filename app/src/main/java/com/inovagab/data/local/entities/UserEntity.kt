package com.inovagab.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val role: String,
    val cargo: String,
    val pontuacao: Int,
    val ideasEnviadas: Int,
    val ideasAprovadas: Int
)
