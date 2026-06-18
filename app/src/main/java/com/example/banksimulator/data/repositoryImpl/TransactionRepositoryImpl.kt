package com.example.banksimulator.data.repositoryImpl

import com.example.banksimulator.data.local.dao.TransactionDao
import com.example.banksimulator.data.mapper.toDomain
import com.example.banksimulator.domain.model.Transaction
import com.example.banksimulator.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
): TransactionRepository {
    override fun getUserTransactions(userId: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForUser(userId).map { transactions ->
            transactions.map { it.toDomain() }
        }
    }

}