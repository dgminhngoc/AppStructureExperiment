package com.example.myapplication.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.myapplication.viewmodel.AppScreen
import com.example.myapplication.viewmodel.IAppViewModel
import localProvider

@Composable
fun App(appViewModel: IAppViewModel = localProvider.current.getViewModel(IAppViewModel::class.java)) {
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