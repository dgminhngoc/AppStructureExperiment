package com.example.myapplication.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.myapplication.screen.InitDataScreen
import com.example.myapplication.screen.MainScreen
import com.example.myapplication.screen.LoginScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.*

val provider = Provider()

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(localProvider provides provider) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(appViewModel: IAppViewModel = localProvider.current.getViewModel(IAppViewModel::class.java)) {
    when(appViewModel.selectedScreenIndexState.value) {
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