package com.inovagab.domain.model

data class RankingItem(
    val id: String,
    val nome: String,
    val pontuacao: Int,
    val medalha: MedalType?
)

enum class MedalType { OURO, PRATA, BRONZE }
