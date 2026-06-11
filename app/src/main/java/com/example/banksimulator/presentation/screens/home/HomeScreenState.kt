package com.example.banksimulator.presentation.screens.home

import com.example.banksimulator.domain.model.Transaction
import java.math.BigDecimal

data class HomeScreenState(
    val userName: String = "",
    val balance: BigDecimal = BigDecimal.ZERO,
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

