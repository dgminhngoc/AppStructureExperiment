package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.viewmodel.*

@Composable
fun LoginScreen(
    loginScreenViewModel: ILoginScreenViewModel =
        ViewModels.get(ILoginScreenViewModel::class.java.name),
    appViewModel: IAppViewModel =
        ViewModels.get(IAppViewModel::class.java.name),
) {
    val appSelectedScreenIndexState by appViewModel.selectedScreenIndexState.collectAsState()
    DisposableEffect(appSelectedScreenIndexState) {
        onDispose {
            if(appSelectedScreenIndexState != AppScreen.LOGIN) {
                loginScreenViewModel.dispose()
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