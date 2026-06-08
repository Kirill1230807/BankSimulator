package com.example.banksimulator.data.local.entity.converters.enums_converters

import androidx.room.TypeConverter
import com.example.banksimulator.data.local.entity.utils.Currency

class CurrencyConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String = currency.name

    @TypeConverter
    fun toCurrency(status: String): Currency = Currency.valueOf(status)
}