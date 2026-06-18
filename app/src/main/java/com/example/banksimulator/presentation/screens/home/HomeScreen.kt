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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.banksimulator.R
import com.example.banksimulator.data.local.entity.AccountEntity
import com.example.banksimulator.data.local.entity.CardEntity
import com.example.banksimulator.data.local.entity.foreignkeys.Card
import com.example.banksimulator.domain.model.Account
import com.example.banksimulator.domain.model.AccountStatus
import com.example.banksimulator.domain.model.AccountType
import com.example.banksimulator.domain.model.CardStatus
import com.example.banksimulator.domain.model.Currency
import com.example.banksimulator.domain.model.Transaction
import com.example.banksimulator.presentation.screens.home.components.CardCarousel
import com.example.banksimulator.presentation.screens.home.components.ProfileIcon
import com.example.banksimulator.presentation.screens.home.components.TransactionRow
import com.example.compose.BankSimulatorTheme
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.ui.platform.LocalLocale

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAccounts: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToCardSettings: (String) -> Unit,
    onNavigateToCreateCard: () -> Unit,
    onLogout: () -> Unit,
    onTransferMoneyClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        state = uiState,
        onAccountsClick = onNavigateToAccounts,
        onHistoryClick = onNavigateToHistory,
        onAddNewCardClick = onNavigateToCreateCard,
        onCardSettingsClick = onNavigateToCardSettings,
        onLogoutClick = {
            viewModel.onSignOutClick()
            onLogout()
        },
        onTransferMoneyClick = onTransferMoneyClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeState,
    onAccountsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onAddNewCardClick: () -> Unit,
    onCardSettingsClick: (String) -> Unit,
    onLogoutClick: () -> Unit,
    onTransferMoneyClick: () -> Unit
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
                ProfileIcon(
                    firstName = state.firstName
                )
                Button(onClick = onAccountsClick) {
                    Text("Усі рахунки")
                }
                IconButton(onClick = onLogoutClick) {
                    Icon(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
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
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            val accountTypeTitle = when (state.account?.accountType) {
                                AccountType.PERSONAL -> "Особистий рахунок"
                                AccountType.SAVINGS -> "Ощадний рахунок"
                                AccountType.CREDIT -> "Кредитний рахунок"
                                AccountType.BUSINESS -> "Бізнес рахунок"
                                null -> "Рахунок"
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = accountTypeTitle,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                        }
                    }
                    item {
                        Text(
                            text = "Мої картки",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                        )
                    }
                    item {
                        CardCarousel(
                            cards = state.cards.map { it.card },
                            onCardSettingsClick = { cardEntity ->
                                onCardSettingsClick(cardEntity.cardId)
                            },
                            onAddNewCardClick = onAddNewCardClick
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${state.balance}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = state.account?.currency?.name ?: "",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = onTransferMoneyClick
                            ) {
                                Text("Переказати кошти")
                            }
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surface)
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
                                    Text(
                                        text = "Поки транзакцій не було 💵",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            } else {
                                val sdf = SimpleDateFormat(
                                    "dd.MM.yyyy",
                                    LocalLocale.current.platformLocale
                                )
                                state.transactions.take(3).forEach { transaction ->
                                    val formattedDate = sdf.format(Date(transaction.createdAt))
                                    TransactionRow(
                                        transactionName = transaction.name,
                                        amount = "${transaction.amount} ${transaction.currency.name}",
                                        date = formattedDate
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
        Card(
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
        Card(
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

    val fakeTransactions: List<Transaction> = listOf(
        Transaction(
            transactionId = "1",
            senderAccountId = "1",
            receiverAccountId = "2",
            name = "Поповнення",
            amount = BigDecimal("500.00"),
            description = "Переказ коштів від людини",
            currency = Currency.UAH,
            status = "COMPLETED",
            createdAt = System.currentTimeMillis()
        ),
        Transaction(
            transactionId = "2",
            senderAccountId = "3",
            receiverAccountId = "1",
            name = "Поповнення",
            amount = BigDecimal("1200.00"),
            description = "Поповнення рахунку",
            currency = Currency.UAH,
            status = "COMPLETED",
            createdAt = System.currentTimeMillis()
        ),
        Transaction(
            transactionId = "1",
            senderAccountId = "1",
            receiverAccountId = "2",
            name = "Переказ коштів",
            amount = BigDecimal("-500.00"),
            description = "Переказ коштів від людини",
            currency = Currency.UAH,
            status = "COMPLETED",
            createdAt = System.currentTimeMillis()
        ),
    )

    val fakeDomainAccount = Account(
        accountId = "1",
        ownerId = "1",
        accountType = AccountType.PERSONAL,
        fullName = "Іван Іванов",
        iban = "UA12345678901234567890123456",
        balance = BigDecimal("15000.50"),
        currency = Currency.UAH,
        status = AccountStatus.ACTIVE,
        createdAt = System.currentTimeMillis()
    )

    BankSimulatorTheme {
        HomeScreenContent(
            state = HomeState(
                firstName = "Іван",
                lastName = "Іванов",
                cards = fakeCards,
                transactions = fakeTransactions,
                balance = BigDecimal("15000.50"),
                account = fakeDomainAccount,
                isLoading = false,
                errorMessage = null,
                isLogout = false
            ),
            onAccountsClick = {},
            onHistoryClick = {},
            onCardSettingsClick = {},
            onAddNewCardClick = {},
            onLogoutClick = {},
            onTransferMoneyClick = {}
        )
    }
}