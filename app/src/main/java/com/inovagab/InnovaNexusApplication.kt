package com.inovagab

import android.app.Application
import com.inovagab.data.local.MockDataSeeder
import com.inovagab.data.local.database.AppDatabase
import com.inovagab.data.remote.api.RetrofitClient
import com.inovagab.data.repository.AuthRepository
import com.inovagab.data.repository.DashboardRepository
import com.inovagab.data.repository.IdeaRepository
import com.inovagab.data.repository.ProjectRepository
import com.inovagab.data.repository.StrategyRepository
import com.inovagab.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/** Application entry point for InnovaNexus. */
class InnovaNexusApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var database: AppDatabase
        private set

    lateinit var authRepository: AuthRepository
        private set
    lateinit var ideaRepository: IdeaRepository
        private set
    lateinit var projectRepository: ProjectRepository
        private set
    lateinit var strategyRepository: StrategyRepository
        private set
    lateinit var userRepository: UserRepository
        private set
    lateinit var dashboardRepository: DashboardRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
        val api = RetrofitClient.apiService
        authRepository = AuthRepository(api, database.userDao())
        ideaRepository = IdeaRepository(database.ideaDao(), api)
        projectRepository = ProjectRepository(database.projectDao(), api)
        strategyRepository = StrategyRepository(database.strategyDao(), api)
        userRepository = UserRepository(database.userDao(), api)
        dashboardRepository = DashboardRepository(api, ideaRepository, projectRepository)

        applicationScope.launch {
            MockDataSeeder.seedIfNeeded(
                database.userDao(),
                database.ideaDao(),
                database.projectDao(),
                database.strategyDao()
            )
        }
    }
}
