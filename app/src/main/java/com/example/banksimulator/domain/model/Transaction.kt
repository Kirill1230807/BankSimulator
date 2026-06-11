package com.example.banksimulator.domain.model

data class Transaction(
    val id: String,
    val title: String,
    val amount: Double,
    val date: String
)
