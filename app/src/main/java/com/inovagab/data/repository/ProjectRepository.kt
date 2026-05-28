package com.inovagab.data.repository

import com.inovagab.data.local.dao.ProjectDao
import com.inovagab.data.mapper.EntityMapper.toDomain
import com.inovagab.data.mapper.EntityMapper.toEntity
import com.inovagab.data.remote.api.ApiService
import com.inovagab.domain.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectRepository(
    private val projectDao: ProjectDao,
    private val apiService: ApiService
) {
    fun observeProjects(): Flow<List<Project>> = projectDao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun getProjectById(id: String): Project? = projectDao.getById(id)?.toDomain()

    suspend fun saveProject(project: Project) {
        projectDao.insert(project.toEntity())
    }
}
