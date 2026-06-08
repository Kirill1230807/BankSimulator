package com.example.banksimulator.presentation.screens.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    fun onFirstNameChange(newFirstName: String) {
        _uiState.update { it.copy(firstName = newFirstName, firstNameError = null) }
    }


    fun onLastNameChange(newLastName: String) {
        _uiState.update { it.copy(lastName = newLastName, lastNameError = null) }
    }


    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, emailError = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, passwordError = null) }
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = newPhoneNumber, phoneNumberError = null) }
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = newConfirmPassword, confirmPasswordError = null) }
    }

    fun register() {
        TODO()
    }


}