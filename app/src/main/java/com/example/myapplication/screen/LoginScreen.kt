package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.example.myapplication.viewmodel.*

@Composable
fun LoginScreen(
    loginScreenViewModel: ILoginScreenViewModel = localAppViewModel.current.loginScreenViewModel,
) {
    Box {
        when (loginScreenViewModel.selectedPageIndexState.value) {
            LoginNavigatePage.LOGIN -> {
                LoginPage()
            }
            LoginNavigatePage.REGISTER -> {
                RegisterPage()
            }
            LoginNavigatePage.RESET_PASSWORD -> {
                ResetPasswordPage()
            }
        }
    }
}