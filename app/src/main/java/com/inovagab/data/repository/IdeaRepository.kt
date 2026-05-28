package com.inovagab.data.repository

import com.inovagab.data.local.dao.IdeaDao
import com.inovagab.data.mapper.EntityMapper.toDomain
import com.inovagab.data.mapper.EntityMapper.toEntity
import com.inovagab.data.remote.api.ApiService
import com.inovagab.domain.model.Idea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IdeaRepository(
    private val ideaDao: IdeaDao,
    private val apiService: ApiService
) {
    fun observeIdeas(): Flow<List<Idea>> = ideaDao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun getIdeaById(id: String): Idea? = ideaDao.getById(id)?.toDomain()

    suspend fun saveIdea(idea: Idea) {
        ideaDao.insert(idea.toEntity())
    }

    suspend fun syncFromApi(): Result<Unit> = runCatching {
        val remote = apiService.getIdeas()
        ideaDao.insertAll(remote.map { dto ->
            com.inovagab.data.local.entities.IdeaEntity(
                dto.id, dto.titulo, dto.descricao, dto.categoria, dto.impacto,
                dto.status, dto.autor, dto.autorId, dto.dataEnvio, dto.score
            )
        })
    }
}
