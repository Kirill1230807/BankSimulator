package com.example.banksimulator.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banksimulator.domain.repository.AccountRepository
import com.example.banksimulator.domain.repository.TransactionRepository
import com.example.banksimulator.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadData() {
        viewModelScope.launch {
            userRepository.getCurrentUserData()
                .flatMapLatest { user ->
                    if (user == null) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Не авторизовано"
                            )
                        }
                        return@flatMapLatest flowOf(Unit)
                    }

                    combine(
                        userRepository.getHomeUserData(user.userId),
                        transactionRepository.getUserTransactions(user.userId),
                        accountRepository.getUserWithAccounts(user.userId)
                    ) { homeData, transactions, accounts ->
                        val mainAccount = accounts.firstOrNull()
                        _uiState.update {
                            it.copy(
                                firstName = homeData?.firstName ?: "",
                                lastName = homeData?.lastName ?: "",
                                transactions = transactions,
                                account = mainAccount,
                                balance = mainAccount?.balance ?: BigDecimal.ZERO,
                                isLoading = false
                            )
                        }
                    }
                }.collect()
        }
    }

    fun onSignOutClick() {
        viewModelScope.launch {
            userRepository.signOut()
            _uiState.update { it.copy(isLogout = true) }
        }
    }
}