package com.example.myapplication.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.myapplication.providers.getViewModel
import com.example.myapplication.viewmodel.AppScreen
import com.example.myapplication.viewmodel.AppViewModel

@Composable
fun App(
    appViewModel: AppViewModel = getViewModel()
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