package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.viewmodel.*

@Composable
fun LoginPage(
    loginPageViewModel: ILoginPageViewModel =
        ViewModels.get(ILoginPageViewModel::class.java),
    loginScreenViewModel: ILoginScreenViewModel =
        ViewModels.get(ILoginScreenViewModel::class.java),
    appViewModel: IAppViewModel =
        ViewModels.get(IAppViewModel::class.java),
) {

    val loginSelectedPageIndexState by loginScreenViewModel.selectedPageIndexState.collectAsState()
    DisposableEffect(loginSelectedPageIndexState) {
        onDispose {
            if(loginSelectedPageIndexState != LoginNavigatePage.LOGIN) {
                loginPageViewModel.dispose()
            }
        }
    }

    val loginFormSubmittedState by loginPageViewModel.loginFormSubmittedState.collectAsState()
    if(loginFormSubmittedState.isSuccessful) {
        loginFormSubmittedState.user?.let {
            appViewModel.onUIEvent(UIEvent.Login(user = it))
        }
    }

    val loginFormState by loginPageViewModel.loginFormState.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(
                start = 30.dp,
                end = 30.dp,
            )
    ) {
        var email = loginFormState.email
        var password = loginFormState.password

        OutlinedTextField(
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
            isError = loginFormState.emailError != null,
            label = { Text("E-Mail") }
        )
        loginFormState.emailError?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            value = password,
            onValueChange = {
                password = it
                loginPageViewModel.onEvent(LoginFormEvent.LoginFormChanged(
                    email = email,
                    password = password,
                ))
            },
            isError = loginFormState.passwordError != null,
            label = { Text("Password") }
        )
        loginFormState.passwordError?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }

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
                loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.REGISTER))
            }
        ) {
            Text(text = "Register")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.RESET_PASSWORD))
            }
        ) {
            Text(text = "Forgot Password")
        }
    }
}