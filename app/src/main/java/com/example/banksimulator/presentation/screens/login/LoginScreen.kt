package com.example.banksimulator.presentation.screens.login

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.BankSimulatorTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onNavigateToMain()
        }
    }

    LoginScreenContent(
        state = uiState,
        onNavigateToRegister = onNavigateToRegister,
        onEmailChange = { viewModel.onEmailChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        onLoginClick = { viewModel.onLoginClick() }
    )
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    onNavigateToRegister: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Вхід", fontSize = 24.sp)
            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = state.emailError != null,
                supportingText = {
                    if (state.emailError != null) {
                        Text(
                            text = state.emailError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = { Text("Пароль") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (isPasswordVisible) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (isPasswordVisible) "Приховати пароль" else "Показати пароль"

                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                isError = state.passwordError != null,
                supportingText = {
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Не маєте аккаунту? ")

                Text(
                    text = "Зареєструватись",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!state.errorMessage.isNullOrEmpty()) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        Button(
            onClick = onLoginClick,
            shape = RoundedCornerShape(
                topEnd = 10.dp,
                topStart = 10.dp,
                bottomEnd = 10.dp,
                bottomStart = 10.dp
            ),
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Ввійти")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    BankSimulatorTheme {
        LoginScreenContent(
            onNavigateToRegister = {},
            state = LoginState(),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {}
        )
    }
}