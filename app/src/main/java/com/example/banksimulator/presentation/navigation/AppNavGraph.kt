package com.example.banksimulator.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.example.banksimulator.presentation.screens.exchangerates.ExchangeRatesScreen
import com.example.banksimulator.presentation.screens.home.HomeScreen
import com.example.banksimulator.presentation.screens.login.LoginScreen
import com.example.banksimulator.presentation.screens.register.RegisterScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar =
        currentDestination?.hasRoute<MainScreen>() == true || currentDestination?.hasRoute<ExchangeRatesScreen>() == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    ) { paddingValues ->


        NavHost(
            navController = navController,
            startDestination = AuthGraph,
            modifier = Modifier.padding(paddingValues)
        ) {
            authNavGraph(navController)
            mainNavGraph(navController)
        }
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<AuthGraph>(startDestination = LoginScreen) {
        composable<LoginScreen> {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(RegisterScreen) },
                onNavigateToMain = {
                    navController.navigate(MainGraph) {
                        popUpTo(AuthGraph) { inclusive = true }
                    }
                }
            )
        }
        composable<RegisterScreen> {
            RegisterScreen(
                onNavigateToMain = {
                    navController.navigate(MainGraph) {
                        popUpTo(AuthGraph) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(LoginScreen) {
                        popUpTo(LoginScreen) { inclusive = true }
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<MainGraph>(startDestination = MainScreen) {
        composable<MainScreen> {
            HomeScreen(
                onNavigateToAccounts = {
                    // Assuming an AccountsScreen destination exists in the navigation schema
                    // navController.navigate(AccountsScreen)
                },
                onLogout = {
                    navController.navigate(AuthGraph) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                onNavigateToHistory = {},
                onNavigateToCardSettings = {}
            )
        }
        composable<ExchangeRatesScreen> {
            ExchangeRatesScreen()
        }
    }
}
