package com.example.myapplication.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.viewmodel.AppScreen
import com.example.myapplication.viewmodel.IAppViewModel

@Composable
fun App(
    appViewModel: IAppViewModel = ViewModels.get(IAppViewModel::class.java)
) {
    when(appViewModel.selectedScreenIndexState.collectAsState().value) {
        AppScreen.INIT_DATA -> {
            InitDataScreen()
        }
        AppScreen.LOGIN -> {
            LoginScreen()
        }
        AppScreen.MAIN -> {
            MainScreen()
        }
    }
}