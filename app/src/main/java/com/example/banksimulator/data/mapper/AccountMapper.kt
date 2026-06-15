package com.example.banksimulator.data.mapper

import com.example.banksimulator.data.local.entity.AccountEntity
import com.example.banksimulator.domain.model.AccountStatus
import com.example.banksimulator.domain.model.AccountType
import com.example.banksimulator.domain.model.Currency
import com.example.banksimulator.data.remote.remoteModel.AccountRemote
import java.math.BigDecimal

fun AccountRemote.toEntity() : AccountEntity {
    return AccountEntity(
        accountId = this.accountId,
        ownerId = this.ownerId,
        accountType = AccountType.valueOf(this.accountType),
        fullName = this.fullName,
        iban = this.iban,
        balance = BigDecimal(this.balance),
        currency = Currency.valueOf(this.currency),
        status = AccountStatus.valueOf(this.status),
        createdAt = this.createdAt
    )
}

fun AccountEntity.toRemote(): AccountRemote {
    return AccountRemote(
        accountId = this.accountId,
        ownerId = this.ownerId,
        accountType = this.accountType.name,
        fullName = this.fullName,
        iban = this.iban,
        balance = this.balance.toPlainString(),
        currency = this.currency.name,
        status = this.status.name,
        createdAt = this.createdAt
    )
}
