package com.example.banksimulator.data.local.entity.foreignkeys

import androidx.room.Embedded
import androidx.room.Relation
import com.example.banksimulator.data.local.entity.AccountEntity
import com.example.banksimulator.data.local.entity.CardEntity

data class Card(
    @Embedded val card: CardEntity,

    @Relation(
        parentColumn = "accountOwnerId",
        entityColumn = "accountId"
    )
    val account: AccountEntity
)
