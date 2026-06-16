package com.example.banksimulator.presentation.screens.home

import com.example.banksimulator.data.local.entity.foreignkeys.Card
import com.example.banksimulator.domain.model.Transaction

data class HomeState(
    val firstName: String = "",
    val lastName: String = "",
    val isLoading: Boolean = false,
    val cards: List<Card> = emptyList(),
    val transactions: List<Transaction> = emptyList(),
    val errorMessage: String? = null,
    val isLogout: Boolean = false,
)

