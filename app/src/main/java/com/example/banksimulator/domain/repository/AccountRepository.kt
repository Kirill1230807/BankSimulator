package com.example.banksimulator.domain.repository

import com.example.banksimulator.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getUserWithAccounts(userId: String): Flow<List<Account>>
}