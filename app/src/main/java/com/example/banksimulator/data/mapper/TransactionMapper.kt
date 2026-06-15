package com.example.banksimulator.data.mapper

import com.example.banksimulator.data.local.entity.TransactionEntity
import com.example.banksimulator.data.remote.remoteModel.TransactionRemote
import com.example.banksimulator.domain.model.Currency
import com.example.banksimulator.domain.model.Transaction
import java.math.BigDecimal

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        transactionId = this.transactionId,
        senderAccountId = this.senderAccountId,
        receiverAccountId = this.receiverAccountId,
        name = this.name,
        amount = this.amount,
        description = this.description,
        currency = this.currency,
        status = this.status,
        createdAt = this.createdAt
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        transactionId = this.transactionId,
        senderAccountId = this.senderAccountId,
        receiverAccountId = this.receiverAccountId,
        name = this.name,
        amount = this.amount,
        description = this.description,
        currency = this.currency,
        status = this.status,
        createdAt = this.createdAt
    )
}

fun TransactionRemote.toEntity(): TransactionEntity {
    return TransactionEntity(
        transactionId = this.transactionId,
        senderAccountId = this.senderAccountId,
        receiverAccountId = this.receiverAccountId,
        name = this.name,
        amount = BigDecimal(this.amount),
        description = this.description,
        currency = Currency.valueOf(this.currency),
        status = this.status,
        createdAt = this.createdAt
    )
}

fun TransactionEntity.toRemote(): TransactionRemote {
    return TransactionRemote(
        transactionId = this.transactionId,
        senderAccountId = this.senderAccountId,
        receiverAccountId = this.receiverAccountId,
        name = this.name,
        amount = this.amount.toPlainString(),
        description = this.description,
        currency = this.currency.name,
        status = this.status,
        createdAt = this.createdAt
    )
}
