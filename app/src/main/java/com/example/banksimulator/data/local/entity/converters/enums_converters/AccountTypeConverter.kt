package com.example.banksimulator.data.local.entity.converters.enums_converters

import androidx.room.TypeConverter
import com.example.banksimulator.data.local.entity.utils.AccountType

class AccountTypeConverter {
    @TypeConverter
    fun fromAccountType(type: AccountType): String = type.name

    @TypeConverter
    fun toAccountType(type: String): AccountType = AccountType.valueOf(type)
}