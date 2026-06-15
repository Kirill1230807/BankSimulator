package com.example.banksimulator.data.mapper

import com.example.banksimulator.data.local.entity.CardEntity
import com.example.banksimulator.data.remote.remoteModel.CardRemote
import com.example.banksimulator.domain.model.Card
import com.example.banksimulator.domain.model.CardStatus

fun CardEntity.toDomain(): Card {
    return Card(
        cardId = this.cardId,
        accountOwnerId = this.accountOwnerId,
        cardNumber = this.cardNumber,
        cardColor = this.cardColor,
        cardHolderName = this.cardHolderName,
        expirationDate = this.expirationDate,
        status = this.status,
        createdAt = this.createdAt
    )
}

fun Card.toEntity(): CardEntity {
    return CardEntity(
        cardId = this.cardId,
        accountOwnerId = this.accountOwnerId,
        cardNumber = this.cardNumber,
        cardColor = this.cardColor,
        cardHolderName = this.cardHolderName,
        expirationDate = this.expirationDate,
        status = this.status,
        createdAt = this.createdAt
    )
}

fun CardEntity.toRemote(): CardRemote {
    return CardRemote(
        cardId = this.cardId,
        accountOwnerId = this.accountOwnerId,
        cardNumber = this.cardNumber,
        cardHolderName = this.cardHolderName,
        cardColor = this.cardColor,
        expirationDate = this.expirationDate,
        status = this.status.name,
        createdAt = this.createdAt
    )
}

fun CardRemote.toEntity(): CardEntity {
    return CardEntity(
        cardId = this.cardId,
        accountOwnerId = this.accountOwnerId,
        cardNumber = this.cardNumber,
        cardColor = this.cardColor,
        cardHolderName = this.cardHolderName,
        expirationDate = this.expirationDate,
        status = CardStatus.valueOf(this.status),
        createdAt = this.createdAt
    )
}