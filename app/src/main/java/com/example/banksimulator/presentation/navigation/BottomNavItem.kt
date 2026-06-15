package com.example.banksimulator.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem<T : Any>(
    val title: String,
    val icon: ImageVector,
    val route: T
) {
    // Твій існуючий MainScreen
    object Home : BottomNavItem<MainScreen>(
        title = "Головна",
        icon = Icons.Default.Home,
        route = MainScreen
    )

    object ExchangeRates : BottomNavItem<ExchangeRatesScreen>(
        title = "Курси",
        icon = Icons.Default.CurrencyExchange,
        route = ExchangeRatesScreen
    )

}