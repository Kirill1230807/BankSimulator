package com.example.banksimulator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.banksimulator.data.local.entity.UserEntity
import com.example.banksimulator.data.local.entity.foreignkeys.HomeUserData
import com.example.banksimulator.data.local.entity.foreignkeys.UserWithAccounts
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: String): Flow<UserEntity?>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserWithAccounts(userId: String): Flow<UserWithAccounts>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getHomeUserData(userId: String): Flow<HomeUserData?>
}