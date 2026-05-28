package com.inovagab.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inovagab.data.local.entities.IdeaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeaDao {
    @Query("SELECT * FROM ideas ORDER BY dataEnvio DESC")
    fun observeAll(): Flow<List<IdeaEntity>>

    @Query("SELECT * FROM ideas WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): IdeaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ideas: List<IdeaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(idea: IdeaEntity)
}
