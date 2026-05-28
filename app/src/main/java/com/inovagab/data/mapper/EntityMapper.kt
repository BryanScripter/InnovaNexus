package com.inovagab.data.mapper

import com.inovagab.data.local.entities.IdeaEntity
import com.inovagab.data.local.entities.ProjectEntity
import com.inovagab.data.local.entities.StrategyEntity
import com.inovagab.data.local.entities.UserEntity
import com.inovagab.data.remote.dto.IdeaDTO
import com.inovagab.data.remote.dto.ProjectDTO
import com.inovagab.data.remote.dto.StrategyDTO
import com.inovagab.domain.model.Idea
import com.inovagab.domain.model.IdeaStatus
import com.inovagab.domain.model.Project
import com.inovagab.domain.model.ProjectStatus
import com.inovagab.domain.model.Strategy
import com.inovagab.domain.model.User
import com.inovagab.domain.model.UserRole

object EntityMapper {

    fun UserEntity.toDomain() = User(
        id = id,
        name = name,
        email = email,
        role = UserRole.from(role),
        cargo = cargo,
        pontuacao = pontuacao,
        ideasEnviadas = ideasEnviadas,
        ideasAprovadas = ideasAprovadas
    )

    fun IdeaEntity.toDomain() = Idea(
        id = id,
        titulo = titulo,
        descricao = descricao,
        categoria = categoria,
        impacto = impacto,
        status = IdeaStatus.from(status),
        autor = autor,
        autorId = autorId,
        dataEnvio = dataEnvio,
        score = score
    )

    fun ProjectEntity.toDomain() = Project(
        id = id,
        nome = nome,
        descricao = descricao,
        status = ProjectStatus.from(status),
        progresso = progresso,
        investimento = investimento,
        retornoEsperado = retornoEsperado,
        prazo = prazo,
        ideiaOrigem = ideiaOrigem
    )

    fun StrategyEntity.toDomain() = Strategy(
        id = id,
        titulo = titulo,
        descricao = descricao,
        icone = icone
    )

    fun Idea.toEntity() = IdeaEntity(
        id = id,
        titulo = titulo,
        descricao = descricao,
        categoria = categoria,
        impacto = impacto,
        status = status.value,
        autor = autor,
        autorId = autorId,
        dataEnvio = dataEnvio,
        score = score
    )

    fun Project.toEntity() = ProjectEntity(
        id = id,
        nome = nome,
        descricao = descricao,
        status = status.value,
        progresso = progresso,
        investimento = investimento,
        retornoEsperado = retornoEsperado,
        prazo = prazo,
        ideiaOrigem = ideiaOrigem
    )

    fun Strategy.toEntity() = StrategyEntity(
        id = id,
        titulo = titulo,
        descricao = descricao,
        icone = icone
    )

    fun IdeaDTO.toDomain() = Idea(
        id = id,
        titulo = titulo,
        descricao = descricao,
        categoria = categoria,
        impacto = impacto,
        status = IdeaStatus.from(status),
        autor = autor,
        autorId = autorId,
        dataEnvio = dataEnvio,
        score = score
    )

    fun ProjectDTO.toDomain() = Project(
        id = id,
        nome = nome,
        descricao = descricao,
        status = ProjectStatus.from(status),
        progresso = progresso,
        investimento = investimento,
        retornoEsperado = retornoEsperado,
        prazo = prazo,
        ideiaOrigem = ideiaOrigem
    )

    fun StrategyDTO.toDomain() = Strategy(
        id = id,
        titulo = titulo,
        descricao = descricao,
        icone = icone
    )
}
