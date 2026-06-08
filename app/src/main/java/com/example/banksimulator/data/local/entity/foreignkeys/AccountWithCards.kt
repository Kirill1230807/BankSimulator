package com.example.banksimulator.data.local.entity.foreignkeys

import androidx.room.Embedded
import androidx.room.Relation
import com.example.banksimulator.data.local.entity.AccountEntity
import com.example.banksimulator.data.local.entity.CardEntity

data class AccountWithCards(
    @Embedded val account: AccountEntity,

    @Relation(
        parentColumn = "accountId",
        entityColumn = "accountOwnerId"
    )
    val cards: List<CardEntity>
)
