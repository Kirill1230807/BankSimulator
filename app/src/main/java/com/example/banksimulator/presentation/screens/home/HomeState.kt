package com.example.banksimulator.presentation.screens.home

import com.example.banksimulator.data.local.entity.foreignkeys.Card
import com.example.banksimulator.domain.model.Account
import com.example.banksimulator.domain.model.Transaction
import java.math.BigDecimal

data class HomeState(
    val firstName: String = "",
    val lastName: String = "",
    val balance: BigDecimal = BigDecimal.ZERO,
    val cards: List<Card> = emptyList(),
    val account: Account? = null,
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLogout: Boolean = false,
)

