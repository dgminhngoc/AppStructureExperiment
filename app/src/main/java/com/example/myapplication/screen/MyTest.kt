package com.example.myapplication.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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

    Column{
        Text(text = "Login: ${loginScreenViewModel.name}")
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                loginScreenViewModel.name = "LoginScreenViewModelTest CHANGED"
                appViewModel.onUIEvent(UIEventTest.Login)
            }
        ) {
            Text(text = "Login")
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

    Column {
        Text(text = "Main: ${mainScreenViewModel.name}")
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                mainScreenViewModel.name = "MainScreenViewModelTest CHANGED"
                appViewModel.onUIEvent(UIEventTest.Logout)
            }
        ) {
            Text(text = "Logout")
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

class LoginScreenViewModelTest: ViewModel() {
    var name:String = "LoginScreenViewModelTest"

    init {
        Log.d("test", "LoginScreenViewModelTest init")
    }

    fun dispose() {
        onCleared()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("test", "LoginScreenViewModelTest onCleared")
    }
}
class MainScreenViewModelTest: ViewModel() {
    var name:String = "MainScreenViewModelTest"

    init {
        Log.d("test", "MainScreenViewModelTest init")
    }

    fun dispose() {
        onCleared()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("test", "MainScreenViewModelTest onCleared")
    }
}