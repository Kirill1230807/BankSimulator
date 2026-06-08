package com.example.banksimulator.data.local.entity.foreignkeys

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.example.banksimulator.data.local.entity.AccountEntity
import com.example.banksimulator.data.local.entity.TransactionEntity

data class AccountWithTransactions(
    @Embedded val account: AccountEntity,

    @Relation(
        parentColumn = "accountId",
        entityColumn = "senderAccountId"
    )
    val sentTransactions: List<TransactionEntity>,

    @Relation(
        parentColumn = "accountId",
        entityColumn = "receiverAccountId"
    )
    val receivedTransactions: List<TransactionEntity>
) {
    @Ignore
    val allTransactions: List<TransactionEntity> = (sentTransactions + receivedTransactions)
}
