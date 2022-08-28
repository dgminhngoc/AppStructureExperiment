package com.example.myapplication.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.IDataRepository
import com.example.myapplication.domain.IUserPreferencesRepository
import com.example.myapplication.domain.UserPreferencesRepository
import com.example.myapplication.models.User
import kotlinx.coroutines.*

sealed class UIEvent {
    data class Login(
        val user: User
    ): UIEvent()

    object Logout: UIEvent()
}

enum class AppScreen{
    INIT_DATA,
    LOGIN,
    MAIN,
}

interface IAppViewModel{
    val selectedScreenIndexState: MutableState<AppScreen>
    fun onUIEvent(event: UIEvent)
}

val localAppViewModel = compositionLocalOf<AppViewModel> { error("AppViewModel not set") }

class AppViewModel(
    private val prefsRepository: IUserPreferencesRepository,
): IAppViewModel, ViewModel() {

    override val selectedScreenIndexState = mutableStateOf(AppScreen.INIT_DATA)

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    init {
         CoroutineScope(defaultDispatcher).launch {
             //init data here
             selectedScreenIndexState.value = AppScreen.LOGIN
        }
    }

    override fun onUIEvent(event: UIEvent) {
        when(event) {
            is UIEvent.Login -> {
                login(event.user)
            }
            is UIEvent.Logout -> {
                logout()
            }
        }
    }

    private fun login(user: User) {
        CoroutineScope(defaultDispatcher).launch {
            prefsRepository.saveUser(user)
        }

        selectedScreenIndexState.value = AppScreen.MAIN
    }

    private fun logout() {
        selectedScreenIndexState.value = AppScreen.LOGIN
    }
}

class AppViewModelFactory(
    private val prefsRepository: UserPreferencesRepository,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(prefsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}