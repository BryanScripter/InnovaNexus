package com.inovagab.data.repository

import com.inovagab.data.local.dao.UserDao
import com.inovagab.data.mapper.EntityMapper.toDomain
import com.inovagab.data.remote.api.ApiService
import com.inovagab.data.remote.dto.LoginRequest
import com.inovagab.domain.model.User
import com.inovagab.domain.model.UserRole
import kotlinx.coroutines.delay

class AuthRepository(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    suspend fun login(email: String, password: String, role: UserRole): Result<User> = runCatching {
        delay(800)
        val mockUser = userDao.getById(
            when (role) {
                UserRole.OPERATOR -> "1"
                UserRole.MANAGER -> "2"
                UserRole.LEADERSHIP -> "3"
            }
        ) ?: error("Usuário não encontrado")
        mockUser.toDomain()
    }

    suspend fun loginApi(email: String, password: String): Result<User> = runCatching {
        val response = apiService.login(LoginRequest(email, password))
        userDao.getById(response.userId)?.toDomain()
            ?: error("Usuário não encontrado após login")
    }
}
