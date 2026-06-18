package com.example.banksimulator.domain.repository

import com.example.banksimulator.domain.model.Transaction
import kotlinx.coroutines.flow.Flow


interface TransactionRepository {
    fun getUserTransactions(userId: String): Flow<List<Transaction>>
}