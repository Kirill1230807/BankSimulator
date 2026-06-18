package com.example.banksimulator.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.banksimulator.R
import com.example.banksimulator.presentation.screens.helpers.toMaskedCardNumber

@Composable
fun BankCard(
    cardNumber: String,
    expirationDate: String,
    onCardSettingsClick: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFF1A237E)
        ),
        modifier = Modifier.size(width = 350.dp, height = 200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onCardSettingsClick,
                    modifier = Modifier.size(30.dp)
                    ) {
                    Icon(
                        painter = painterResource(R.drawable.settings),
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Картка універсальна",
                        color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = cardNumber.toMaskedCardNumber(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )

                        Text(
                            text = expirationDate,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun BankCardPreview() {
    BankCard(
        cardNumber = "9659963296574316",
        expirationDate = "07/30",
        onCardSettingsClick = {}
    )
}