package com.example.banksimulator.domain.repository

import com.example.banksimulator.domain.model.Account
import com.example.banksimulator.domain.model.Transaction
import com.example.banksimulator.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signUpWithEmail(
        email: String,
        password: String,
    ): String

    suspend fun saveUserProfile(user: User)
    fun getCurrentUserData(): Flow<User?>
    fun getHomeUserData(userId: String): Flow<User?>
    fun hasUser(): Boolean
    suspend fun signOut()
}