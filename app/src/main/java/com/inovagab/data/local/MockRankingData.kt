package com.inovagab.data.local

import com.inovagab.domain.model.MedalType
import com.inovagab.domain.model.RankingItem

object MockRankingData {
    val items = listOf(
        RankingItem("1", "Ana Costa", 2850, MedalType.OURO),
        RankingItem("2", "Carlos Silva", 2400, MedalType.PRATA),
        RankingItem("3", "Pedro Alves", 2100, MedalType.BRONZE),
        RankingItem("4", "Lucia Mendes", 1850, null),
        RankingItem("5", "João Santos", 1600, null),
        RankingItem("6", "Maria Oliveira", 1450, null),
        RankingItem("7", "Ricardo Ferreira", 1200, null),
        RankingItem("8", "Fernanda Lima", 980, null)
    )
}
