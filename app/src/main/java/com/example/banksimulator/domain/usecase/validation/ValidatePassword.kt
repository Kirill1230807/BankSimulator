package com.example.banksimulator.domain.usecase.validation

import javax.inject.Inject

class ValidatePassword @Inject constructor() {
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Пароль не може бути порожнім"
            )
        }

        if (password.length < 8) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Пароль має містити мінімум 8 символів"
            )
        }

        val containsLettersAndDigits = password.any { it.isDigit() } && password.any { it.isLetter() }

        if (!containsLettersAndDigits) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Пароль має містити цифри та літери"
            )
        }
        return ValidationResult(isSuccessful = true)
    }
}