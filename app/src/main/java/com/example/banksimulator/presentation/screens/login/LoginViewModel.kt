package com.example.banksimulator.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banksimulator.domain.usecase.LoginUseCase
import com.example.banksimulator.domain.usecase.validation.ValidateEmail
import com.example.banksimulator.domain.usecase.validation.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmail: ValidateEmail
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, emailError = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, passwordError = null) }
    }

    fun onLoginClick() {
        val state = _uiState.value

        val emailResult = validateEmail(state.email)
        val passwordResult = if (state.password.isBlank()) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Пароль не може бути порожнім"
            )
        } else {
            ValidationResult(isSuccessful = true)
        }

        val hasError = !emailResult.isSuccessful || !passwordResult.isSuccessful

        if (hasError) {
            _uiState.update {
                it.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = loginUseCase(
                email = state.email,
                password = state.password
            )
            result.onSuccess {
                _uiState.update {
                    it.copy(isLoading = false, isLoginSuccessful = true)
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Виникла помилка при вході в застосунок"
                    )
                }
            }
        }
    }
}