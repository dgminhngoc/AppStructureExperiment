package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.providers.LocalViewModelProvider
import com.example.myapplication.providers.ViewModelProvider
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.viewmodel.*

@Preview(
    widthDp = 400,
    heightDp = 600
)
@Composable
fun LoginPagePreview() {
    val provider = ViewModelProvider()
    CompositionLocalProvider(LocalViewModelProvider provides provider) {
        LoginPage()
    }
}

@Composable
fun LoginPage(
    loginPageViewModel: ILoginPageViewModel =
        ViewModels.get(ILoginPageViewModel::class.java.name),
    loginScreenViewModel: ILoginScreenViewModel =
        ViewModels.get(ILoginScreenViewModel::class.java.name),
    appViewModel: IAppViewModel =
        ViewModels.get(IAppViewModel::class.java.name),
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
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(
                start = 30.dp,
                end = 30.dp,
            )
    ) {
        var email = loginFormState.email
        var password = loginFormState.password
        Text(
            modifier = Modifier
                .padding(top = 20.dp),
            text = "LOGIN",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            fontSize = 30.sp
        )
        Box{
            Column {
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
                    placeholder = { Text("E-Mail") }
                )
                loginFormState.emailError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 10.dp
                        ),
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
                    placeholder = { Text("Password") }
                )
                loginFormState.passwordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                        .clickable {
                            loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.RESET_PASSWORD))
                        },
                    text = "Forgot Password",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
        Box(
            modifier = Modifier.padding(
                bottom = 20.dp
            )
        ) {
            Column {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        loginPageViewModel.onEvent(LoginFormEvent.LoginFormSubmit)
                    }
                ) {
                    Text(text = "Login")
                }
                Text(text = "You don't have an account?")
                Text(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                        .clickable {
                            loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.REGISTER))
                        },
                    text = "Register",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}