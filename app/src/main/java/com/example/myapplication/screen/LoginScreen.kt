package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.example.myapplication.viewmodel.*

@Composable
fun LoginScreen(
    loginScreenViewModel: ILoginScreenViewModel = localViewModelProvider.current.getViewModel(ILoginScreenViewModel::class.java),
    appViewModel: IAppViewModel = localAppViewModel.current,
) {
    DisposableEffect(appViewModel.selectedScreenIndexState.value) {
        onDispose {
            if(appViewModel.selectedScreenIndexState.value != AppScreen.LOGIN) {
                loginScreenViewModel.onCleared()
            }
        }
    }
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