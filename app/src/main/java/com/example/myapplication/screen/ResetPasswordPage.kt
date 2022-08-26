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
fun ResetPasswordPage(
    resetPasswordPageViewModel: ResetPasswordPageViewModel,
    loginScreenViewModel: LoginScreenViewModel
) {
    if(!resetPasswordPageViewModel.resetPasswordSubmittedState.value) {
        ResetPasswordFormPage(
            viewModel = resetPasswordPageViewModel,
            loginScreenViewModel = loginScreenViewModel
        )
    }
    else{
        ResetPasswordSuccessPage(
            loginScreenViewModel = loginScreenViewModel
        )
    }
}

@Composable
fun ResetPasswordFormPage(
    viewModel: ResetPasswordPageViewModel,
    loginScreenViewModel: LoginScreenViewModel
) {
    var email by remember { mutableStateOf(viewModel.resetPasswordFormState.value.email) }

    Box {
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

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = email,
                onValueChange = {
                    email = it
                    viewModel.onEvent(
                        ResetPasswordFormEvent.ResetPasswordFormChanged(
                            email = email,
                        ))
                },
                isError = viewModel.resetPasswordFormState.value.emailError != null,
                label = { Text("E-Mail") }
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onEvent(ResetPasswordFormEvent.ResetPasswordFormSubmit)
                }
            ) {
                Text(text = "Reset Password")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginPage.LOGIN))
                }
            ) {
                Text(text = "Cancel")
            }
        }
    }
}

@Composable
fun ResetPasswordSuccessPage(
    loginScreenViewModel: LoginScreenViewModel
) {
    Box {
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
            Text(text = "An E-Mail is sent to you")

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginPage.LOGIN))
                }
            ) {
                Text(text = "Back to login screen")
            }
        }
    }
}