package com.example.banksimulator.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.banksimulator.domain.model.CardStatus

@Entity(
    tableName = "cards",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["accountId"],
            childColumns = ["accountOwnerId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["accountOwnerId"])
    ])
data class CardEntity(
    @PrimaryKey val cardId: String,
    val accountOwnerId: String,
    val cardNumber: String,
    val cardColor: Long,
    val cardHolderName: String,
    val expirationDate: String,
    val status: CardStatus,
    val createdAt: Long = System.currentTimeMillis()
)

//    val cvv: String, не зберігати локально
//    val pin: String, не зберігати локально