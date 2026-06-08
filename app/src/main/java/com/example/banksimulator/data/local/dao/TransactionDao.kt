package com.example.banksimulator.data.local.dao

import androidx.room.*
import com.example.banksimulator.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionRecord(transaction: TransactionEntity)

    @Query("UPDATE accounts SET balance = balance + :amount WHERE accountId = :accountId")
    suspend fun updateAccountBalance(accountId: String, amount: BigDecimal)

    @Transaction
    suspend fun executeTransaction(
        senderAccountId: String,
        receiverAccountId: String,
        amount: BigDecimal,
        transactionRecord: TransactionEntity
    ) {
        updateAccountBalance(senderAccountId, -amount)

        updateAccountBalance(receiverAccountId, amount)

        insertTransactionRecord(transactionRecord)
    }

    @Query("SELECT * FROM transactions WHERE transactionId = :transactionId")
    suspend fun getTransactionById(transactionId: String): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE senderAccountId = :accountId OR receiverAccountId = :accountId ORDER BY createdAt DESC")
    fun getTransactionsForAccount(accountId: String): Flow<List<TransactionEntity>>
}