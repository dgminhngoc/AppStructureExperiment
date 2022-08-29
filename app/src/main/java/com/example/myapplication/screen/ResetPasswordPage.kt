package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.viewmodel.*

@Composable
fun ResetPasswordPage(
    resetPasswordPageViewModel: IResetPasswordPageViewModel =
        ViewModels.get(IResetPasswordPageViewModel::class.java),
    loginScreenViewModel: ILoginScreenViewModel =
        ViewModels.get(ILoginScreenViewModel::class.java),
) {
    val loginSelectedPageIndexState by loginScreenViewModel.selectedPageIndexState.collectAsState()
    DisposableEffect(loginSelectedPageIndexState) {
        onDispose {
            if(loginSelectedPageIndexState != LoginNavigatePage.RESET_PASSWORD) {
                resetPasswordPageViewModel.onCleared()
            }
        }
    }

    if(!resetPasswordPageViewModel.resetPasswordSubmittedState.value) {
        ResetPasswordFormPage(
            resetPasswordPageViewModel = resetPasswordPageViewModel,
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
    resetPasswordPageViewModel: IResetPasswordPageViewModel,
    loginScreenViewModel: ILoginScreenViewModel
) {
    var email = resetPasswordPageViewModel.resetPasswordFormState.value.email

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

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = email,
                onValueChange = {
                    email = it
                    resetPasswordPageViewModel.onEvent(
                        ResetPasswordFormEvent.ResetPasswordFormChanged(
                            email = email,
                        ))
                },
                isError = resetPasswordPageViewModel.resetPasswordFormState.value.emailError != null,
                label = { Text("E-Mail") }
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    resetPasswordPageViewModel.onEvent(ResetPasswordFormEvent.ResetPasswordFormSubmit)
                }
            ) {
                Text(text = "Reset Password")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.LOGIN))
                }
            ) {
                Text(text = "Cancel")
            }
        }
    }
}

@Composable
fun ResetPasswordSuccessPage(
    loginScreenViewModel: ILoginScreenViewModel
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
                    loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.LOGIN))
                }
            ) {
                Text(text = "Back to login screen")
            }
        }
    }
}