package com.example.banksimulator.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banksimulator.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val user = userRepository.getCurrentUserData().firstOrNull()
            val userId = user?.userId ?: run {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Користувача не знайдено") }
                return@launch
            }

            launch {
                userRepository.getHomeUserData(userId).collect { homeData ->
                    if (homeData != null) {
                        _uiState.update { state ->
                            state.copy(
                                firstName = homeData.user.firstName,
                                lastName = homeData.user.lastName,
                                isLoading = false
                            )
                        }
                    }
                }
            }

            launch {
                userRepository.getUserTransactions(userId).collect { transaction ->
                    _uiState.update { state ->
                        state.copy(transactions = transaction)
                    }
                }
            }
        }
    }

    fun onSignOutClick() {
        viewModelScope.launch {
            userRepository.signOut()
            _uiState.update { HomeState() }
        }
    }
}