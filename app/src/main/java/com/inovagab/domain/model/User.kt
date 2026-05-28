package com.inovagab.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val cargo: String,
    val pontuacao: Int,
    val ideasEnviadas: Int,
    val ideasAprovadas: Int
)

enum class UserRole(val value: String) {
    OPERATOR("operador"),
    MANAGER("gestor"),
    LEADERSHIP("lideranca");

    companion object {
        fun from(value: String): UserRole = entries.first { it.value == value }
    }
}
