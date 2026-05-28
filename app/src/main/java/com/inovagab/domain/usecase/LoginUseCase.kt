package com.inovagab.domain.usecase

import com.inovagab.data.repository.AuthRepository
import com.inovagab.domain.model.User
import com.inovagab.domain.model.UserRole

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, role: UserRole): Result<User> {
        return authRepository.login(email, password, role)
    }
}
