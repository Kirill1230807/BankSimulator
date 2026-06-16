package com.example.banksimulator.data.local.entity.converters.enums_converters

import androidx.room.TypeConverter
import com.example.banksimulator.domain.model.AccountStatus

class AccountStatusConverter {
    @TypeConverter
    fun fromAccountStatus(status: AccountStatus): String = status.name

    @TypeConverter
    fun toAccountStatus(status: String): AccountStatus = AccountStatus.valueOf(status)
}