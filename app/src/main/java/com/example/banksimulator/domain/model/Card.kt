package com.example.banksimulator.domain.model

data class Card(
    val cardId: String,
    val accountOwnerId: String,
    val cardNumber: String,
    val cardColor: Long,
    val cardHolderName: String,
    val expirationDate: String,
    val status: CardStatus,
    val createdAt: Long
)