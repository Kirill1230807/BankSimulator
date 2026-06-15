package com.example.banksimulator.data.local.entity.foreignkeys

import androidx.room.Embedded
import androidx.room.Relation
import com.example.banksimulator.data.local.entity.AccountEntity
import com.example.banksimulator.data.local.entity.UserEntity

data class HomeUserData(
    @Embedded val user: UserEntity,

    @Relation(
        parentColumn = "userId",
        entityColumn = "ownerId"
    )
    val account: List<AccountEntity>
)
