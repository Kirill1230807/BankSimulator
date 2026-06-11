package com.example.banksimulator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.banksimulator.data.local.entity.CardEntity
import com.example.banksimulator.data.local.entity.foreignkeys.AccountWithCards
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCard(card: CardEntity)

    @Update
    suspend fun updateCard(card: CardEntity)

    @Query("SELECT * FROM cards WHERE cardId = :cardId")
    suspend fun getCardById(cardId: Int): CardEntity?

    @Transaction
    @Query("SELECT * FROM accounts WHERE accountId = :accountId")
    fun getCardByAccountId(accountId: String): Flow<AccountWithCards>

    @Query("SELECT accountOwnerId FROM cards WHERE cardNumber = :cardNumber LIMIT 1")
    suspend fun getCardByNumber(cardNumber: String): String?
}