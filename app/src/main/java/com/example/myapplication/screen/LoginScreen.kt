package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.example.myapplication.viewmodel.*
import localProvider

@Composable
fun LoginScreen(
    loginScreenViewModel: ILoginScreenViewModel = localProvider.current.getViewModel(ILoginScreenViewModel::class.java),
    appViewModel: IAppViewModel = localProvider.current.getViewModel(IAppViewModel::class.java),
) {
    val appSelectedScreenIndexState by appViewModel.selectedScreenIndexState.collectAsState()
    DisposableEffect(appSelectedScreenIndexState) {
        onDispose {
            if(appSelectedScreenIndexState != AppScreen.LOGIN) {
                loginScreenViewModel.onCleared()
            }
        }
    }

    Box {
        when (loginScreenViewModel.selectedPageIndexState.collectAsState().value) {
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