package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.RequestResult
import com.example.myapplication.providers.LocalViewModelProvider
import com.example.myapplication.providers.ViewModelStoreImpl
import com.example.myapplication.providers.getViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.*

@Preview(
    widthDp = 400,
    heightDp = 800
)
@Composable
fun LoginPagePreview() {
    val provider = ViewModelStoreImpl()
    MyApplicationTheme {
        CompositionLocalProvider(LocalViewModelProvider provides provider) {
            LoginPage()
        }
    }
}

@Composable
fun LoginPage(
    loginPageViewModel: LoginPageViewModel = getViewModel(),
    loginScreenViewModel: LoginScreenViewModel = getViewModel(),
    appViewModel: AppViewModel = getViewModel(),
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

    BoxWithConstraints {
        val constraintsScope = this
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = constraintsScope.maxHeight <= 400.dp
                )
                .padding(start = 30.dp, end = 30.dp),
        ) {
            var email = loginFormState.email
            var password = loginFormState.password
            val isPasswordVisible = loginFormState.isPasswordVisible
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Login",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface,
                fontSize = 25.sp
            )
            Box{
                Column {
                    val focusManager = LocalFocusManager.current
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = loginPageUIState !is LoginPageUIState.RequestSending,
                        value = email,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.onSurface,
                        ),
                        onValueChange = {
                            email = it
                            loginPageViewModel.onEvent(LoginFormEvent.LoginFormChanged(
                                email = email,
                                password = password,
                                isPasswordVisible = isPasswordVisible
                            ))
                        },
                        isError = loginFormState.emailError != null,
                        placeholder = { Text("E-Mail") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                    )
                    loginFormState.emailError?.let {
                        Text(
                            text = it.asString(),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.onSurface,
                        ),
                        enabled = loginPageUIState !is LoginPageUIState.RequestSending,
                        visualTransformation =
                        if (isPasswordVisible) {
                            VisualTransformation.None
                        }
                        else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                loginPageViewModel.onEvent(LoginFormEvent.LoginFormSubmit)
                            }
                        ),
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
                        maxLines = 1,
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
                                        isPasswordVisible = !isPasswordVisible
                                    ))
                                }
                            ){
                                Icon(imageVector  = image, description)
                            }
                        }
                    )
                    loginFormState.passwordError?.let {
                        Text(
                            text = it.asString(),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .clickable {
                                loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.RESET_PASSWORD))
                            },
                        text = "Forgot Password",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
            Box(modifier = Modifier.padding(bottom = 20.dp)) {
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
                            },
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = "You don't have an account?",
                        color = MaterialTheme.colors.onSurface,
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp)
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
}