package com.inovagab.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inovagab.data.local.dao.IdeaDao
import com.inovagab.data.local.dao.ProjectDao
import com.inovagab.data.local.dao.StrategyDao
import com.inovagab.data.local.dao.UserDao
import com.inovagab.data.local.entities.IdeaEntity
import com.inovagab.data.local.entities.ProjectEntity
import com.inovagab.data.local.entities.StrategyEntity
import com.inovagab.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        IdeaEntity::class,
        ProjectEntity::class,
        StrategyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun ideaDao(): IdeaDao
    abstract fun projectDao(): ProjectDao
    abstract fun strategyDao(): StrategyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inovagab.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
