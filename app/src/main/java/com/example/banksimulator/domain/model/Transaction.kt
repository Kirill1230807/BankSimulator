package com.example.banksimulator.domain.model

import java.math.BigDecimal

data class Transaction(
    val transactionId: String,
    val senderAccountId: String,
    val receiverAccountId: String,
    val name: String,
    val amount: BigDecimal,
    val description: String?,
    val currency: Currency,
    val status: String,
    val createdAt: Long
)