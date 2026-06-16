package com.example.banksimulator.domain.usecase.validation

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)
