package com.example.banksimulator.presentation.screens.home

import com.example.banksimulator.data.local.entity.TransactionEntity
import com.example.banksimulator.data.local.entity.foreignkeys.CardWithAccount

data class HomeState(
    val firstName: String = "",
    val lastName: String = "",
    val isLoading: Boolean = false,
    val cards: List<CardWithAccount> = emptyList(),
    val transactions: List<TransactionEntity> = emptyList(),
    val errorMessage: String? = null
)

