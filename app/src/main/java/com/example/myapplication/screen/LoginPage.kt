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
    loginPageViewModel: LoginPageViewModel,
    loginScreenViewModel: LoginScreenViewModel,
    appViewModel: AppViewModel,
) {
    if(loginPageViewModel.loginFormSubmittedState.value.isSuccessful) {
        loginPageViewModel.loginFormSubmittedState.value.user?.let {
            appViewModel.onUIEvent(UIEvent.Login(user = it))
        }
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
        var email by remember { mutableStateOf(loginPageViewModel.loginFormState.value.email) }
        var password by remember { mutableStateOf(loginPageViewModel.loginFormState.value.password) }

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
                loginPageViewModel.onEvent(LoginFormEvent.LoginFormChanged(
                    email = email,
                    password = password,
                ))
            },
            isError = loginPageViewModel.loginFormState.value.emailError != null,
            label = { Text("E-Mail") }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
                loginPageViewModel.onEvent(LoginFormEvent.LoginFormChanged(
                    email = email,
                    password = password,
                ))
            },
            isError = loginPageViewModel.loginFormState.value.passwordError != null,
            label = { Text("Password") }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                loginPageViewModel.onEvent(LoginFormEvent.LoginFormSubmit)
            }
        ) {
            Text(text = "Login")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginPage.REGISTER))
            }
        ) {
            Text(text = "Register")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginPage.RESET_PASSWORD))
            }
        ) {
            Text(text = "Forgot Password")
        }
    }
}