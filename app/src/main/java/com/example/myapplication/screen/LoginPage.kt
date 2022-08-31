package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.RequestResult
import com.example.myapplication.providers.LocalViewModelProvider
import com.example.myapplication.providers.ViewModelProvider
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.*

@Preview(
    widthDp = 400,
    heightDp = 400
)
@Composable
fun LoginPagePreview() {
    val provider = ViewModelProvider()
    MyApplicationTheme {
        CompositionLocalProvider(LocalViewModelProvider provides provider) {
            LoginPage()
        }
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

    val loginPageUIState by loginPageViewModel.loginPageUIState.collectAsState()
    when(loginPageUIState) {
        is LoginPageUIState.ResultReceived -> {
            when(val requestResult = (loginPageUIState as LoginPageUIState.ResultReceived).requestResult) {
                is RequestResult.OnSuccess -> {
                    requestResult.data?.let {
                        appViewModel.onUIEvent(UIEvent.Login(user = it))
                    }
                }
                is RequestResult.OnError -> {

                }
             }
        }
        else ->{}
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
        val isPasswordVisible = loginFormState.isPasswordVisible
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
                    enabled = loginPageUIState !is LoginPageUIState.RequestSending,
                    value = email,
                    onValueChange = {
                        email = it
                        loginPageViewModel.onEvent(LoginFormEvent.LoginFormChanged(
                            email = email,
                            password = password,
                            isPasswordVisible = isPasswordVisible
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
                    enabled = loginPageUIState !is LoginPageUIState.RequestSending,
                    visualTransformation =
                        if (isPasswordVisible) {
                            VisualTransformation.None
                        }
                        else {
                            PasswordVisualTransformation()
                        },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    value = password,
                    onValueChange = {
                        password = it
                        loginPageViewModel.onEvent(LoginFormEvent.LoginFormChanged(
                            email = email,
                            password = password,
                            isPasswordVisible = isPasswordVisible
                        ))
                    },
                    isError = loginFormState.passwordError != null,
                    placeholder = { Text("Password") },
                    trailingIcon = {
                        val image = if (isPasswordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        // Please provide localized description for accessibility services
                        val description = if (isPasswordVisible) "Hide password" else "Show password"

                        IconButton(
                            onClick = {
                                loginPageViewModel.onEvent(LoginFormEvent.LoginFormChanged(
                                    email = email,
                                    password = password,
                                    isPasswordVisible = isPasswordVisible
                                ))
                            }
                        ){
                            Icon(imageVector  = image, description)
                        }
                    }
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
                            top = 5.dp
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
                    },
                    enabled = loginPageUIState !is LoginPageUIState.RequestSending
                ) {
                    Text(
                        text = if(loginPageUIState is LoginPageUIState.RequestSending) {
                            "Logging in..."
                        } else {
                            "Login"
                        }
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(
                            top = 5.dp
                        ),
                    text = "You don't have an account?"
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 5.dp
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