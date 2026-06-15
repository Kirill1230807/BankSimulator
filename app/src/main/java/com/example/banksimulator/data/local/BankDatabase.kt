package com.example.banksimulator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.banksimulator.data.local.dao.AccountDao
import com.example.banksimulator.data.local.dao.CardDao
import com.example.banksimulator.data.local.dao.TransactionDao
import com.example.banksimulator.data.local.dao.UserDao
import com.example.banksimulator.data.local.entity.AccountEntity
import com.example.banksimulator.data.local.entity.CardEntity
import com.example.banksimulator.data.local.entity.TransactionEntity
import com.example.banksimulator.data.local.entity.UserEntity
import com.example.banksimulator.data.local.entity.converters.enums_converters.AccountStatusConverter
import com.example.banksimulator.data.local.entity.converters.enums_converters.AccountTypeConverter
import com.example.banksimulator.data.local.entity.converters.enums_converters.CardStatusConverter
import com.example.banksimulator.data.local.entity.converters.enums_converters.CurrencyConverter
import com.example.banksimulator.data.local.entity.converters.system_converters.BigDecimalConverter
import com.example.banksimulator.data.local.entity.converters.system_converters.InstantConverter
import com.example.banksimulator.data.local.entity.converters.system_converters.UUIDConverter


@Database(
    entities = [
        UserEntity::class,
        AccountEntity::class,
        CardEntity::class,
        TransactionEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    AccountStatusConverter::class,
    AccountTypeConverter::class,
    CardStatusConverter::class,
    CurrencyConverter::class,
    BigDecimalConverter::class,
    InstantConverter::class,
    UUIDConverter::class
)
abstract class BankDatabase : RoomDatabase() {
    abstract fun usersDao(): UserDao
    abstract fun accountsDao(): AccountDao
    abstract fun cardDao(): CardDao
    abstract fun transactionDao(): TransactionDao
}