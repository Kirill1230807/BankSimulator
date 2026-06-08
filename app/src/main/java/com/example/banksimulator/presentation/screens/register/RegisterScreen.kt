package com.example.banksimulator.presentation.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RegisterScreenContent(
        onFirstNameChange = { viewModel.onFirstNameChange(it) },
        onLastNameChange = { viewModel.onLastNameChange(it) },
        onEmailChange = { viewModel.onEmailChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        onPhoneNumberChange = { viewModel.onPhoneNumberChange(it) },
        onConfirmPasswordChange = { viewModel.onConfirmPasswordChange(it) }
    )
}

@Composable
private fun RegisterScreenContent(
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreenContent(
        onFirstNameChange = {},
        onLastNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onPhoneNumberChange = {},
        onConfirmPasswordChange = {}
    )
}