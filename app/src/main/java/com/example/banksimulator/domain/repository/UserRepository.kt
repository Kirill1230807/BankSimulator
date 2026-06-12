package com.example.banksimulator.domain.repository

import com.example.banksimulator.data.local.entity.TransactionEntity
import com.example.banksimulator.data.local.entity.UserEntity
import com.example.banksimulator.data.local.entity.foreignkeys.HomeUserData
import com.example.banksimulator.data.local.entity.foreignkeys.UserWithAccounts
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String
        ): Result<Unit>
    fun getUserWithAccounts(userId: String): Flow<UserWithAccounts>
    fun getCurrentUserData(): Flow<UserEntity?>
    fun getHomeUserData(userId: String): Flow<HomeUserData?>
    fun getUserTransactions(userId: String): Flow<List<TransactionEntity>>
    fun hasUser(): Boolean
    suspend fun signOut()
}