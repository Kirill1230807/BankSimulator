package com.example.banksimulator.domain.model

import java.math.BigDecimal

data class Account(
    val accountId: String,
    val ownerId: String,
    val accountType: AccountType,
    val fullName: String,
    val iban: String,
    val balance: BigDecimal,
    val currency: Currency,
    val status: AccountStatus,
    val createdAt: Long
)