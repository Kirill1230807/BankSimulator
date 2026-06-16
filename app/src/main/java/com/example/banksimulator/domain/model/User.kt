package com.example.banksimulator.domain.model

data class User(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val createdAt: Long = System.currentTimeMillis()
)