package com.inovagab.data.repository

import com.inovagab.data.local.dao.StrategyDao
import com.inovagab.data.mapper.EntityMapper.toDomain
import com.inovagab.data.mapper.EntityMapper.toEntity
import com.inovagab.data.remote.api.ApiService
import com.inovagab.domain.model.Strategy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StrategyRepository(
    private val strategyDao: StrategyDao,
    private val apiService: ApiService
) {
    fun observeStrategies(): Flow<List<Strategy>> = strategyDao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun saveStrategy(strategy: Strategy) {
        strategyDao.insert(strategy.toEntity())
    }

    suspend fun deleteStrategy(strategy: Strategy) {
        strategyDao.delete(strategy.toEntity())
    }
}
