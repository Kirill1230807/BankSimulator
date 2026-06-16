package com.example.banksimulator.data.local.entity.converters.enums_converters

import androidx.room.TypeConverter
import com.example.banksimulator.domain.model.CardStatus

class CardStatusConverter {
    @TypeConverter
    fun fromCardStatus(status: CardStatus): String = status.name

    @TypeConverter
    fun toCardStatus(status: String): CardStatus = CardStatus.valueOf(status)
}