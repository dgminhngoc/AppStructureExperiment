package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.*

@Composable
fun LoginPage(
    viewModel: LoginPageViewModel,
) {
    val loginScreenViewModel = localLoginScreenViewModel.current
    if(viewModel.loginFormSubmittedState.value) {
        localAppViewModel.current.onUIEvent(UIEvent.Login)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(
                start = 30.dp,
                end = 30.dp,
            )
    ) {
        var email by remember { mutableStateOf(viewModel.loginFormState.value.email) }
        var password by remember { mutableStateOf(viewModel.loginFormState.value.password) }

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
                viewModel.onEvent(LoginFormEvent.LoginFormChanged(
                    email = email,
                    password = password,
                ))
            },
            isError = viewModel.loginFormState.value.emailError != null,
            label = { Text("Username") }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
                viewModel.onEvent(LoginFormEvent.LoginFormChanged(
                    email = email,
                    password = password,
                ))
            },
            isError = viewModel.loginFormState.value.passwordError != null,
            label = { Text("Password") }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.onEvent(LoginFormEvent.LoginFormSubmit)
            }
        ) {
            Text(text = "Login")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                loginScreenViewModel.selectPage(LoginPage.REGISTER)
            }
        ) {
            Text(text = "Register")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                loginScreenViewModel.selectPage(LoginPage.RESET_PASSWORD)
            }
        ) {
            Text(text = "Forgot Password")
        }
    }
}