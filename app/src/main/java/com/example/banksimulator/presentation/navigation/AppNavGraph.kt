package com.example.banksimulator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.banksimulator.presentation.screens.home.HomeScreen
import com.example.banksimulator.presentation.screens.login.LoginScreen
import com.example.banksimulator.presentation.screens.register.RegisterScreen

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
            LoginScreen(
                onNavigateToRegister = { navController.navigate(RegisterScreen) },
                onNavigateToMain = { navController.navigate(MainGraph) }
            )
        }
        composable<RegisterScreen> {
            RegisterScreen(
                onNavigateToMain = { navController.navigate(MainGraph) },
                onNavigateToLogin = { navController.navigate(LoginScreen) })
        }
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<MainGraph>(startDestination = MainScreen) {
        composable<MainScreen> {
            HomeScreen()
        }
    }
}
