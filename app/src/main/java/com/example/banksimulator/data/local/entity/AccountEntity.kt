package com.example.banksimulator.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.banksimulator.domain.model.AccountStatus
import com.example.banksimulator.domain.model.AccountType
import com.example.banksimulator.domain.model.Currency
import java.math.BigDecimal

@Entity(
    tableName = "accounts",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["ownerId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["ownerId"])
    ]
)
data class AccountEntity(
    @PrimaryKey val accountId: String,
    val ownerId: String,
    val accountType: AccountType,
    val fullName: String,
    val iban: String,
    val balance: BigDecimal,
    val currency: Currency,
    val status: AccountStatus = AccountStatus.ACTIVE,
    val createdAt: Long = System.currentTimeMillis()
)