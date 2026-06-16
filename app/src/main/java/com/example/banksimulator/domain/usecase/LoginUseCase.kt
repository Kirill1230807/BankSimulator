package com.example.banksimulator.domain.usecase

import com.example.banksimulator.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit> {
        return userRepository.login(email, password)
    }
}