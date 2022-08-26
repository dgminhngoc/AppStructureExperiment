package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*

import com.example.myapplication.viewmodel.LoginPage
import com.example.myapplication.viewmodel.LoginScreenViewModel
import com.example.myapplication.viewmodel.localAppViewModel
import com.example.myapplication.viewmodel.localLoginScreenViewModel

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
) {
    CompositionLocalProvider(localLoginScreenViewModel provides viewModel) {
        Box {
            when(viewModel.selectedPageIndexState.value) {
                LoginPage.LOGIN -> {
                    LoginPage(
                        viewModel = viewModel.loginPageViewModel,
                        loginScreenViewModel = viewModel,
                        appViewModel = localAppViewModel.current
                    )
                }
                LoginPage.REGISTER -> {
                    RegisterPage(
                        viewModel = viewModel.registrationPageViewModel,
                        loginScreenViewModel = viewModel
                    )
                }
                LoginPage.RESET_PASSWORD -> {
                    ResetPasswordPage(
                        viewModel = viewModel.resetPasswordPageViewModel,
                        loginScreenViewModel = viewModel
                    )
                }
            }
        }
    }
}