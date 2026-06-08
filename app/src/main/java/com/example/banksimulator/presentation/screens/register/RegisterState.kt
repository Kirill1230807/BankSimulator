package com.example.banksimulator.presentation.screens.register

data class RegisterState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val confirmPassword: String = "",

    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val phoneNumberError: String? = null,
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val isRegistrationSuccessful: Boolean = false,
    val generatedErrorMessage: String? = null
)
