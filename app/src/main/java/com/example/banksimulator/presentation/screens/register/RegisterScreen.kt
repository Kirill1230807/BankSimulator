package com.example.banksimulator.presentation.screens.register

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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.banksimulator.core.ui.theme.BankSimulatorTheme

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = uiState.isRegistrationSuccessful) {
        if (uiState.isRegistrationSuccessful) {
            onNavigateToMain()
        }
    }

    RegisterScreenContent(
        state = uiState,
        onFirstNameChange = { viewModel.onFirstNameChange(it) },
        onLastNameChange = { viewModel.onLastNameChange(it) },
        onEmailChange = { viewModel.onEmailChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        onPhoneNumberChange = { viewModel.onPhoneNumberChange(it) },
        onConfirmPasswordChange = { viewModel.onConfirmPasswordChange(it) },
        onRegistrationClick = { viewModel.onRegisterClick() },
        onNavigateToLogin = onNavigateToLogin,
        onFirstNameFocusChanged = { viewModel.onFirstNameFocusChanged(it) },
        onLastNameFocusChanged = { viewModel.onLastNameFocusChanged(it) },
        onPhoneNumberFocusChanged = { viewModel.onPhoneNumberFocusChanged(it) },
        onEmailFocusChanged = { viewModel.onEmailFocusChanged(it) },
        onPasswordFocusChanged = { viewModel.onPasswordFocusChanged(it) },
        onConfirmPasswordFocusChanged = { viewModel.onConfirmPasswordFocusChanged(it) }
    )
}

@Composable
private fun RegisterScreenContent(
    state: RegisterState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegistrationClick: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onFirstNameFocusChanged: (Boolean) -> Unit,
    onLastNameFocusChanged: (Boolean) -> Unit,
    onPhoneNumberFocusChanged: (Boolean) -> Unit,
    onEmailFocusChanged: (Boolean) -> Unit,
    onPasswordFocusChanged: (Boolean) -> Unit,
    onConfirmPasswordFocusChanged: (Boolean) -> Unit,

    ) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    var firstNameGainedFocus by remember { mutableStateOf(false) }
    var lastNameGainedFocus by remember { mutableStateOf(false) }
    var emailGainedFocus by remember { mutableStateOf(false) }
    var passwordGainedFocus by remember { mutableStateOf(false) }
    var confirmPasswordGainedFocus by remember { mutableStateOf(false) }
    var phoneNumberGainedFocus by remember { mutableStateOf(false) }

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

            Text("Реєстрація", fontSize = 24.sp)
            OutlinedTextField(
                value = state.firstName,
                onValueChange = onFirstNameChange,
                label = { Text("Ім'я") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            firstNameGainedFocus = true
                        }

                        if (!focusState.isFocused && firstNameGainedFocus) {
                            onFirstNameFocusChanged(false)
                        }
                    },
                singleLine = true,
                isError = state.firstNameError != null,
                supportingText = {
                    if (state.firstNameError != null) {
                        Text(
                            text = state.firstNameError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )


            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.lastName,
                onValueChange = onLastNameChange,
                label = { Text("Прізвище") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            lastNameGainedFocus = true
                        }

                        if (!focusState.isFocused && lastNameGainedFocus) {
                            onLastNameFocusChanged(false)
                        }
                    },
                singleLine = true,
                isError = state.lastNameError != null,
                supportingText = {
                    if (state.lastNameError != null) {
                        Text(
                            text = state.lastNameError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            emailGainedFocus = true
                        }

                        if (!focusState.isFocused && emailGainedFocus) {
                            onEmailFocusChanged(false)
                        }
                    },
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
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            passwordGainedFocus = true
                        }

                        if (!focusState.isFocused && passwordGainedFocus) {
                            onPasswordFocusChanged(false)
                        }
                    },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (isPasswordVisible) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description =
                        if (isPasswordVisible) "Приховати пароль" else "Показати пароль"

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

            OutlinedTextField(
                value = state.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("Підтвердіть пароль ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            confirmPasswordGainedFocus = true
                        }

                        if (!focusState.isFocused && confirmPasswordGainedFocus) {
                            onConfirmPasswordFocusChanged(false)
                        }
                    },
                singleLine = true,
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (isConfirmPasswordVisible) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description =
                        if (isConfirmPasswordVisible) "Приховати пароль" else "Показати пароль"

                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                isError = state.confirmPasswordError != null,
                supportingText = {
                    if (state.confirmPasswordError != null) {
                        Text(
                            text = state.confirmPasswordError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.phoneNumber,
                onValueChange = onPhoneNumberChange,
                label = { Text("Номер телефону") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            phoneNumberGainedFocus = true
                        }

                        if (!focusState.isFocused && phoneNumberGainedFocus) {
                            onPhoneNumberFocusChanged(false)
                        }
                    },
                singleLine = true,
                isError = state.phoneNumberError != null,
                supportingText = {
                    if (state.phoneNumberError != null) {
                        Text(
                            text = state.phoneNumberError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Вже є акаунт?")

                Text(
                    text = "Увійти",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }

        Button(
            onClick = onRegistrationClick,
            shape = RoundedCornerShape(
                topEnd = 10.dp,
                topStart = 10.dp,
                bottomEnd = 10.dp,
                bottomStart = 10.dp
            ),
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Зареєструватися")
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    BankSimulatorTheme {
        RegisterScreenContent(
            onFirstNameChange = {},
            onLastNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onPhoneNumberChange = {},
            onConfirmPasswordChange = {},
            onRegistrationClick = {},
            onNavigateToLogin = {},
            state = RegisterState(),
            onFirstNameFocusChanged = {},
            onLastNameFocusChanged = {},
            onPhoneNumberFocusChanged = {},
            onEmailFocusChanged = {},
            onPasswordFocusChanged = {},
            onConfirmPasswordFocusChanged = {}
        )
    }
}