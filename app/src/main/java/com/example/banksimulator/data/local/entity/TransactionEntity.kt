package com.example.banksimulator.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.banksimulator.data.local.entity.utils.Currency
import java.math.BigDecimal

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["accountId"],
            childColumns = ["senderAccountId"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["accountId"],
            childColumns = ["receiverAccountId"],
            onDelete = ForeignKey.RESTRICT,
        )
    ],
    indices = [
        Index(value = ["senderAccountId"]),
        Index(value = ["receiverAccountId"])
    ])
data class TransactionEntity(
    @PrimaryKey val transactionId: String,
    val senderAccountId: String,
    val receiverAccountId: String,
    val amount: BigDecimal,
    val description: String?,
    val currency: Currency,
    val status: String,
    val createdAt: Long = System.currentTimeMillis()
)