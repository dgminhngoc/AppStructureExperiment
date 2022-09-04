package com.example.myapplication.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.viewmodel.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class UIEventTest {
    object Login: UIEventTest()
    object Logout: UIEventTest()
}

@Composable
fun AppNavTest(
    appViewModel: AppViewModelTest = viewModel()
) {
    val navController = rememberNavController()
    BackHandler {

    }
    NavHost(navController = navController, startDestination = "init_screen") {
        composable(route = "init_screen") {
            InitDataScreen()
        }
        composable(route = "login_screen") {
            LoginScreenTest(appViewModel = appViewModel)
        }
        composable(route = "main_screen") {
            MainScreenTest(appViewModel = appViewModel)
        }
    }

    when(appViewModel.selectedScreenIndexState.collectAsState().value) {
        AppScreen.INIT_DATA -> {
            navController.navigate("init_screen")
        }
        AppScreen.LOGIN -> {
            navController.navigate("login_screen")
        }
        AppScreen.MAIN -> {
            navController.navigate("main_screen")
        }
    }
}

@Composable
fun AppTest(
    appViewModel: AppViewModelTest = viewModel()
) {
    when(appViewModel.selectedScreenIndexState.collectAsState().value) {
        AppScreen.INIT_DATA -> {
            InitDataScreen()
        }
        AppScreen.LOGIN -> {
            LoginScreenTest(appViewModel = appViewModel)
        }
        AppScreen.MAIN -> {
            MainScreenTest(appViewModel = appViewModel)
        }
    }
}

@Composable
fun LoginScreenTest(
    loginScreenViewModel: LoginScreenViewModelTest = viewModel(),
    appViewModel: AppViewModelTest
) {
    val appSelectedScreenIndexState by appViewModel.selectedScreenIndexState.collectAsState()
    DisposableEffect(appSelectedScreenIndexState) {
        onDispose {
            if(appSelectedScreenIndexState != AppScreen.LOGIN) {
                loginScreenViewModel.dispose()
            }
        }
    }

    val loginScreenState = loginScreenViewModel.loginScreenState.collectAsState()

    Box(modifier = Modifier.background(color = loginScreenState.value.backgroundColor)) {
        Column(
            verticalArrangement = Arrangement.Center,
        ){
            Text(text = "Login: ${loginScreenState.value.name}")
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loginScreenViewModel.change()
                }
            ) {
                Text(text = "Change")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    appViewModel.onUIEvent(UIEventTest.Login)
                }
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Composable
fun MainScreenTest(
    mainScreenViewModel: MainScreenViewModelTest = viewModel(),
    appViewModel: AppViewModelTest
) {
    val appSelectedScreenIndexState by appViewModel.selectedScreenIndexState.collectAsState()
    DisposableEffect(appSelectedScreenIndexState) {
        onDispose {
            if(appSelectedScreenIndexState != AppScreen.MAIN) {
                mainScreenViewModel.dispose()
            }
        }
    }
    val mainScreenState = mainScreenViewModel.mainScreenState.collectAsState()
    Box(modifier = Modifier.background(color = mainScreenState.value.backgroundColor)) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Main: ${mainScreenState.value.name}")
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    mainScreenViewModel.change()
                }
            ) {
                Text(text = "Change")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    appViewModel.onUIEvent(UIEventTest.Logout)
                }
            ) {
                Text(text = "Logout")
            }
        }
    }
}

class AppViewModelTest: ViewModel() {

    private val _selectedScreenIndexState = MutableStateFlow(AppScreen.INIT_DATA)
    val selectedScreenIndexState= _selectedScreenIndexState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _selectedScreenIndexState.value
    )

    init {
        //init data here
        _selectedScreenIndexState.value = AppScreen.LOGIN
    }

    fun onUIEvent(event: UIEventTest) {
        when(event) {
            is UIEventTest.Login -> {
                login()
            }
            is UIEventTest.Logout -> {
                logout()
            }
        }
    }

    private fun login() {
        _selectedScreenIndexState.value = AppScreen.MAIN
    }

    private fun logout() {
        _selectedScreenIndexState.value = AppScreen.LOGIN
    }

    init {
        Log.d("test", "AppViewModelTest init")
    }

    fun dispose() {
        onCleared()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("test", "AppViewModelTest onCleared")
    }
}

data class LoginScreenState(
    val name: String = "LoginScreenViewModelTest",
    val backgroundColor: Color= Color.White
)

class LoginScreenViewModelTest: ViewModel() {

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState= _loginScreenState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _loginScreenState.value
    )

    init {
        Log.d("test", "LoginScreenViewModelTest init")
    }

    fun change() {
        _loginScreenState.value = LoginScreenState(
            name = "LoginScreenViewModelTest CHANGED",
            backgroundColor = Color.Blue
        )
    }

    fun dispose() {
        onCleared()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("test", "LoginScreenViewModelTest onCleared")
    }
}

data class MainScreenState(
    val name: String = "MainScreenViewModelTest",
    val backgroundColor: Color= Color.White
)

class MainScreenViewModelTest: ViewModel() {
    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreenState= _mainScreenState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _mainScreenState.value
    )

    init {
        Log.d("test", "MainScreenViewModelTest init")
    }

    fun change() {
        _mainScreenState.value = MainScreenState(
            name = "MainScreenViewModelTest CHANGED",
            backgroundColor = Color.Green
        )
    }

    fun dispose() {
        onCleared()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("test", "MainScreenViewModelTest onCleared")
    }
}