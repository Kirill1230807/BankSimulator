package com.example.banksimulator.domain.usecase.validation

import javax.inject.Inject

class ValidateName @Inject constructor() {
    operator fun invoke(name: String, fieldName: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "$fieldName не може бути порожнім"
            )
        }

        if (name.length < 2) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "$fieldName має містити мінімум 2 символи"
            )
        }

        // Регулярний вираз: тільки англійські літери АБО тільки українські літери
        val englishRegex = Regex("^[a-zA-Z]+$")
        val ukrainianRegex = Regex("^[а-яА-ЯіІїЇєЄґҐ']+$")

        if (!englishRegex.matches(name) && !ukrainianRegex.matches(name)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "$fieldName має містити тільки літери (англійською або українською)"
            )
        }

        return ValidationResult(isSuccessful = true)
    }
}
