package com.inovagab.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inovagab.data.local.entities.StrategyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StrategyDao {
    @Query("SELECT * FROM strategies")
    fun observeAll(): Flow<List<StrategyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(strategies: List<StrategyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(strategy: StrategyEntity)

    @Delete
    suspend fun delete(strategy: StrategyEntity)
}
