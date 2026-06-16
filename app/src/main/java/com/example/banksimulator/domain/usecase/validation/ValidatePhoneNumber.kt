package com.example.banksimulator.domain.usecase.validation

import javax.inject.Inject

class ValidatePhoneNumber @Inject constructor() {
    operator fun invoke(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Номер телефону не може бути порожнім"
            )
        }

        // Регулярний вираз для формату +380XXXXXXXXX
        val phoneRegex = Regex("^\\+380\\d{9}$")
        
        if (!phoneRegex.matches(phoneNumber)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Невірний формат номера (приклад: +380961234567)"
            )
        }
        
        return ValidationResult(isSuccessful = true)
    }
}
