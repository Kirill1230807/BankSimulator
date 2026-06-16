package com.example.banksimulator.domain.usecase.validation

import android.util.Patterns
import javax.inject.Inject

class ValidateEmail @Inject constructor() {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Email не може бути порожнім"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Невірний формат email"
            )
        }
        return ValidationResult(isSuccessful = true)
    }
}