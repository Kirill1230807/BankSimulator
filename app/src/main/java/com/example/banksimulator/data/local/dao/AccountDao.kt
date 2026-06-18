package com.example.banksimulator.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.banksimulator.data.local.entity.AccountEntity
import com.example.banksimulator.data.local.entity.foreignkeys.AccountWithCards
import com.example.banksimulator.data.local.entity.foreignkeys.AccountWithTransactions
import com.example.banksimulator.data.local.entity.foreignkeys.UserWithAccounts
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts WHERE accountId = :accountId")
    suspend fun getAccountById(accountId: String): AccountEntity

    @Query("SELECT * FROM accounts WHERE ownerId = :ownerId")
    fun getAccountByUserId(ownerId: String): Flow<List<AccountEntity>>

    @Transaction
    @Query("SELECT * FROM accounts WHERE accountId = :accountId")
    fun getAccountWithCards(accountId: String): Flow<AccountWithCards>

    @Transaction
    @Query("SELECT * FROM accounts WHERE accountId = :accountId")
    fun getAccountWithTransactions(accountId: String): Flow<AccountWithTransactions>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserWithAccounts(userId: String): Flow<UserWithAccounts>
}