package com.example.myapplication.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.DataRepository
import com.example.myapplication.domain.UserPreferencesRepository
import com.example.myapplication.screen.InitDataScreen
import com.example.myapplication.screen.MainScreen
import com.example.myapplication.screen.LoginScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.*

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    App(appViewModel = appViewModel)
                }

            }
        }
    }
}

@Composable
fun App(appViewModel: IAppViewModel) {
    when(appViewModel.selectedScreenIndexState.value) {
        AppScreen.INIT_DATA -> {
            InitDataScreen()
        }
        AppScreen.LOGIN -> {
            LoginScreen()
        }
        AppScreen.MAIN -> {
            MainScreen(mainScreenViewModel = localAppViewModel.current.mainScreenViewModel)
        }
    }
}