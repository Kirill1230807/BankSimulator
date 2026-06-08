package com.example.banksimulator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthGraph
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<AuthGraph>(startDestination = LoginScreen) {
        composable<LoginScreen> {
            // LoginScreenContent(navController)
        }
        composable<RegisterScreen> {
            // RegistrationScreenContent(navController)
        }
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<MainGraph>(startDestination = MainScreen) {
        composable<MainScreen> {
            // MainScreenContent(navController)
        }
    }
}
