package com.example.myapplication.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.IDataRepository
import com.example.myapplication.domain.UserPreferencesRepository

sealed class UIEvent {
    object Login: UIEvent()
    object Logout: UIEvent()
}

enum class AppScreen{
    INIT_DATA,
    LOGIN,
    MAIN,
}

val localAppViewModel = compositionLocalOf<AppViewModel> { error("AppViewModel not set") }

class AppViewModel(
    private val dataRepository: IDataRepository,
    private val prefsRepository: UserPreferencesRepository,
): ViewModel() {

    // Keep the user preferences as a stream of changes
//    private val userPreferencesFlow = prefsRepository.userPreferencesFlow

    val selectedScreenIndexState: MutableState<AppScreen> = mutableStateOf(AppScreen.INIT_DATA)

    ///AppViewModel has instance of sub-viewmodels to store current states of app
    ///Storing current states is quite importance for responsive design, that view re-composed very
    ///often. For example when user change size of the app (on tablets or foldable devices), display
    ///language change,...the Activity will surely be recreated. When states aren't saved, almost
    ///everything would be reset and users would lose their progress.
    ///These sub-viewmodels should be nullable, so they can be cleared by GC.
    //_loginScreenViewModel should be assigned NULL when not needed
    private var _loginScreenViewModel: LoginScreenViewModel? = null
    val loginScreenViewModel: LoginScreenViewModel
        get() {
            _loginScreenViewModel = _loginScreenViewModel ?: LoginScreenViewModel(dataRepository)
            return _loginScreenViewModel!!
        }
    //_mainScreenViewModel should be assigned NULL when not needed
    private var _mainScreenViewModel: MainScreenViewModel? = null
    val mainScreenViewModel: MainScreenViewModel
        get() {
            _mainScreenViewModel = _mainScreenViewModel ?: MainScreenViewModel()
            return _mainScreenViewModel!!
        }

    init {
        prefsRepository.initUserData()
        selectedScreenIndexState.value = AppScreen.LOGIN
    }

    private fun initRepositories() {

    }

    fun onUIEvent(event: UIEvent) {
        when(event) {
            is UIEvent.Login -> {
                login()
            }
            is UIEvent.Logout -> {
                logout()
            }
        }
    }

    private fun login() {
        selectedScreenIndexState.value = AppScreen.MAIN

        _loginScreenViewModel?.dispose()
        _loginScreenViewModel = null
    }

    private fun logout() {
        selectedScreenIndexState.value = AppScreen.LOGIN

        _mainScreenViewModel?.dispose()
        _mainScreenViewModel = null
    }
}

class AppViewModelFactory(
    private val dataRepository: IDataRepository,
    private val prefsRepository: UserPreferencesRepository,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(dataRepository, prefsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}