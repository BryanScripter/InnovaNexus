package com.inovagab.data.remote.api

import com.inovagab.data.remote.dto.DashboardDTO
import com.inovagab.data.remote.dto.IdeaDTO
import com.inovagab.data.remote.dto.LoginRequest
import com.inovagab.data.remote.dto.LoginResponse
import com.inovagab.data.remote.dto.ProjectDTO
import com.inovagab.data.remote.dto.StrategyDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("strategies")
    suspend fun getStrategies(): List<StrategyDTO>

    @POST("strategies")
    suspend fun createStrategy(@Body strategy: StrategyDTO): StrategyDTO

    @PUT("strategies/{id}")
    suspend fun updateStrategy(@Path("id") id: String, @Body strategy: StrategyDTO): StrategyDTO

    @DELETE("strategies/{id}")
    suspend fun deleteStrategy(@Path("id") id: String)

    @GET("ideas")
    suspend fun getIdeas(): List<IdeaDTO>

    @POST("ideas")
    suspend fun createIdea(@Body idea: IdeaDTO): IdeaDTO

    @PUT("ideas/{id}")
    suspend fun updateIdea(@Path("id") id: String, @Body idea: IdeaDTO): IdeaDTO

    @GET("projects")
    suspend fun getProjects(): List<ProjectDTO>

    @POST("projects")
    suspend fun createProject(@Body project: ProjectDTO): ProjectDTO

    @PUT("projects/{id}")
    suspend fun updateProject(@Path("id") id: String, @Body project: ProjectDTO): ProjectDTO

    @GET("dashboard")
    suspend fun getDashboard(): DashboardDTO
}
