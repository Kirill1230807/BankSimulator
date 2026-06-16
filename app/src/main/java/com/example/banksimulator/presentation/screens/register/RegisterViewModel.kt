package com.example.banksimulator.presentation.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banksimulator.domain.usecase.RegisterUseCase
import com.example.banksimulator.domain.usecase.validation.ValidateEmail
import com.example.banksimulator.domain.usecase.validation.ValidateName
import com.example.banksimulator.domain.usecase.validation.ValidatePassword
import com.example.banksimulator.domain.usecase.validation.ValidatePhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validatePhoneNumber: ValidatePhoneNumber,
    private val validateName: ValidateName
) : ViewModel() {
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
        _uiState.update {
            it.copy(
                confirmPassword = newConfirmPassword,
                confirmPasswordError = null
            )
        }
    }

    fun onFirstNameFocusChanged(isFocused: Boolean) {
        if (!isFocused) {
            val state = _uiState.value
            val result = validateName(name = state.firstName, fieldName = "Ім'я")
            if (!result.isSuccessful) {
                _uiState.update { it.copy(firstNameError = result.errorMessage) }
            }
        }
    }

    fun onLastNameFocusChanged(isFocused: Boolean) {
        if (!isFocused) {
            val state = _uiState.value
            val result = validateName(name = state.lastName, fieldName = "Прізвище")
            if (!result.isSuccessful) {
                _uiState.update { it.copy(lastNameError = result.errorMessage) }
            }
        }
    }

    fun onPhoneNumberFocusChanged(isFocused: Boolean) {
        if (!isFocused) {
            val state = _uiState.value
            val phoneResult = validatePhoneNumber(phoneNumber = state.phoneNumber)
            if (!phoneResult.isSuccessful) {
                _uiState.update { it.copy(phoneNumberError = phoneResult.errorMessage) }
            }
        }
    }

    fun onEmailFocusChanged(isFocused: Boolean) {
        if (!isFocused) {
            val state = _uiState.value
            val emailResult = validateEmail(email = state.email)
            if (!emailResult.isSuccessful) {
                _uiState.update { it.copy(emailError = emailResult.errorMessage) }
            }
        }
    }

    fun onPasswordFocusChanged(isFocused: Boolean) {
        if (!isFocused) {
            val state = _uiState.value
            val passwordResult = validatePassword(password = state.password)
            if (!passwordResult.isSuccessful) {
                _uiState.update { it.copy(passwordError = passwordResult.errorMessage) }
            }
        }
    }

    fun onConfirmPasswordFocusChanged(isFocused: Boolean) {
        if (!isFocused) {
            val state = _uiState.value
            if (state.password != state.confirmPassword) {
                _uiState.update { it.copy(confirmPasswordError = "Паролі не збігаються") }
            }
        }
    }

    fun onRegisterClick() {
        val state = _uiState.value
        val emailResult = validateEmail(email = state.email)
        val passwordResult = validatePassword(password = state.password)
        val phoneResult = validatePhoneNumber(phoneNumber = state.phoneNumber)
        val firstNameResult = validateName(name = state.firstName, fieldName = "Ім'я")
        val lastNameResult = validateName(name = state.lastName, fieldName = "Прізвище")

        val passwordsMatch = state.password == state.confirmPassword
        val confirmPasswordError = if (!passwordsMatch) "Паролі не збігаються" else null

        val hasError = listOf(
            emailResult,
            passwordResult,
            phoneResult,
            firstNameResult,
            lastNameResult
        ).any { !it.isSuccessful } || !passwordsMatch

        if (hasError) {
            _uiState.update {
                it.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                    phoneNumberError = phoneResult.errorMessage,
                    firstNameError = firstNameResult.errorMessage,
                    lastNameError = lastNameResult.errorMessage,
                    confirmPasswordError = confirmPasswordError,
                    isLoading = false
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                emailError = null,
                passwordError = null,
                phoneNumberError = null,
                confirmPasswordError = null,
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            val result = registerUseCase(
                firstName = state.firstName,
                lastName = state.lastName,
                email = state.email,
                password = state.password,
                phoneNumber = state.phoneNumber
            )

            result.onSuccess {
                _uiState.update {
                    it.copy(isLoading = false, isRegistrationSuccessful = true)
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Виникла помилка"
                    )
                }
            }
        }
    }
}