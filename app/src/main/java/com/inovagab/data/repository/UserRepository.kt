package com.inovagab.data.repository

import com.inovagab.data.local.dao.UserDao
import com.inovagab.data.mapper.EntityMapper.toDomain
import com.inovagab.data.remote.api.ApiService
import com.inovagab.domain.model.User

class UserRepository(
    private val userDao: UserDao,
    private val apiService: ApiService
) {
    suspend fun getUserById(id: String): User? = userDao.getById(id)?.toDomain()
}
