package com.example.banksimulator.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(password: String): Result<Unit>
    fun getUserWithAccounts(userId: String): Flow<Unit>
    suspend fun logout()
}