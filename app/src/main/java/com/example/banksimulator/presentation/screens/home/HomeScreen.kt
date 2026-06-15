package com.example.banksimulator.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.banksimulator.R
import com.example.banksimulator.core.ui.theme.BankSimulatorTheme
import com.example.banksimulator.data.local.entity.AccountEntity
import com.example.banksimulator.data.local.entity.CardEntity
import com.example.banksimulator.data.local.entity.TransactionEntity
import com.example.banksimulator.data.local.entity.foreignkeys.CardWithAccount
import com.example.banksimulator.data.local.entity.utils.AccountStatus
import com.example.banksimulator.data.local.entity.utils.AccountType
import com.example.banksimulator.data.local.entity.utils.CardStatus
import com.example.banksimulator.data.local.entity.utils.Currency
import com.example.banksimulator.presentation.screens.home.components.CardCarousel
import com.example.banksimulator.presentation.screens.home.components.TransactionRow
import java.math.BigDecimal

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAccounts: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToCardSettings: (String) -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        state = uiState,
        onAccountsClick = onNavigateToAccounts,
        onHistoryClick = onNavigateToHistory,
        onCardSettingsClick = onNavigateToCardSettings,
        onLogoutClick = {
            viewModel.onSignOutClick()
            onLogout()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeState,
    onAccountsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onCardSettingsClick: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Привіт, ${state.firstName} ${state.lastName}",
                    modifier = Modifier.padding(end = 16.dp)
                )
                Button(onClick = onAccountsClick) {
                    Text("Усі рахунки")
                }
                IconButton(onClick = onLogoutClick) {
                    Icon(painter = painterResource(R.drawable.logout), contentDescription = null)
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    item {
                        Text(
                            text = "Мої картки",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    item {
                        CardCarousel(
                            cards = state.cards.map { it.card },
                            onCardSettingsClick = { cardEntity ->
                                onCardSettingsClick(cardEntity.cardId)
                            },
                            onAddNewCardClick = { }
                        )
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 12.dp, end = 8.dp, bottom = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Останні транзакції",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Історія",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onHistoryClick() }
                                        .padding(8.dp)
                                )
                            }

                            if (state.transactions.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Поки транзакцій не було 💵", color = Color.Gray)
                                }
                            } else {
                                state.transactions.take(3).forEach { transaction ->
                                    TransactionRow(
                                        transactionName = transaction.name,
                                        amount = transaction.amount.toString(),
                                        date = transaction.createdAt.toString()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    val fakeAccount = AccountEntity(
        accountId = "1",
        ownerId = "1",
        accountType = AccountType.PERSONAL,
        fullName = "Іван Іванов",
        iban = "UA12345678901234567890123456",
        balance = BigDecimal("15000.50"),
        currency = Currency.UAH,
        status = AccountStatus.ACTIVE
    )

    val fakeCards = listOf(
        CardWithAccount(
            card = CardEntity(
                cardId = "1",
                accountOwnerId = "1",
                cardNumber = "4444 4444 4444 4444",
                cardColor = 0xFF1A1A1A,
                cardHolderName = "IVAN IVANOV",
                expirationDate = "12/26",
                status = CardStatus.ACTIVE
            ),
            account = fakeAccount
        ),
        CardWithAccount(
            card = CardEntity(
                cardId = "2",
                accountOwnerId = "1",
                cardNumber = "5555 5555 5555 5555",
                cardColor = 0xFF2D2D2D,
                cardHolderName = "IVAN IVANOV",
                expirationDate = "10/25",
                status = CardStatus.ACTIVE
            ),
            account = fakeAccount
        )
    )

    val fakeTransactions = listOf(
        TransactionEntity(
            transactionId = "1",
            senderAccountId = "1",
            receiverAccountId = "2",
            name = "Переказ коштів",
            amount = BigDecimal("500.00"),
            description = "Переказ коштів від людини",
            currency = Currency.UAH,
            status = "COMPLETED"
        ),
        TransactionEntity(
            transactionId = "2",
            senderAccountId = "3",
            receiverAccountId = "1",
            name = "Поповнення",
            amount = BigDecimal("1200.00"),
            description = "Поповнення рахунку",
            currency = Currency.UAH,
            status = "COMPLETED"
        ),
        TransactionEntity(
            transactionId = "1",
            senderAccountId = "1",
            receiverAccountId = "2",
            name = "Переказ коштів",
            amount = BigDecimal("-500.00"),
            description = "Переказ коштів від людини",
            currency = Currency.UAH,
            status = "COMPLETED"
        ),
    )

    BankSimulatorTheme {
        HomeScreenContent(
            state = HomeState(
                firstName = "Іван",
                lastName = "Іванов",
                cards = fakeCards,
                transactions = fakeTransactions
            ),
            onAccountsClick = {},
            onHistoryClick = {},
            onCardSettingsClick = {},
            onLogoutClick = {}
        )
    }
}