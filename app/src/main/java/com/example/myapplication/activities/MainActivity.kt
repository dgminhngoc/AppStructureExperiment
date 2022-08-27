package com.example.myapplication.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.domain.DataRepository
import com.example.myapplication.domain.UserPreferencesRepository
import com.example.myapplication.screen.InitDataScreen
import com.example.myapplication.screen.LoginScreen
import com.example.myapplication.screen.MainScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.AppScreen
import com.example.myapplication.viewmodel.AppViewModel
import com.example.myapplication.viewmodel.AppViewModelFactory
import com.example.myapplication.viewmodel.localAppViewModel

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "MainActivity:onCreate")
        val appViewModel = ViewModelProvider(
            this,
            AppViewModelFactory(
                dataRepository = DataRepository(),
                prefsRepository = UserPreferencesRepository(dataStore)
            )
        )[AppViewModel::class.java]
        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(localAppViewModel provides appViewModel) {
                    App()
                }

            }
        }
    }
}

@Composable
fun App(appViewModel: AppViewModel = viewModel()) {
    when (appViewModel.selectedScreenIndexState.value) {
        AppScreen.INIT_DATA -> {
            InitDataScreen()
        }
        AppScreen.LOGIN -> {
            LoginScreen()
        }
        AppScreen.MAIN -> {
            MainScreen(mainScreenViewModel = appViewModel.mainScreenViewModel)
        }
    }
}