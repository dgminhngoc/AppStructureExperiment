package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*

import com.example.myapplication.viewmodel.LoginPage
import com.example.myapplication.viewmodel.LoginScreenViewModel
import com.example.myapplication.viewmodel.localAppViewModel

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel,
) {
    Box {
        when(loginScreenViewModel.selectedPageIndexState.value) {
            LoginPage.LOGIN -> {
                LoginPage(
                    loginPageViewModel = loginScreenViewModel.loginPageViewModel,
                    loginScreenViewModel = loginScreenViewModel,
                    appViewModel = localAppViewModel.current
                )
            }
            LoginPage.REGISTER -> {
                RegisterPage(
                    registrationPageViewModel = loginScreenViewModel.registrationPageViewModel,
                    loginScreenViewModel = loginScreenViewModel
                )
            }
            LoginPage.RESET_PASSWORD -> {
                ResetPasswordPage(
                    resetPasswordPageViewModel = loginScreenViewModel.resetPasswordPageViewModel,
                    loginScreenViewModel = loginScreenViewModel
                )
            }
        }
    }
}