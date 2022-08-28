package com.example.myapplication.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.domain.IUserPreferencesRepository
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

abstract class IAppViewModel: IViewModel(){
    abstract val selectedScreenIndexState: MutableState<AppScreen>
    abstract fun onUIEvent(event: UIEvent)
}

class AppViewModel(
    private val prefsRepository: IUserPreferencesRepository,
): IAppViewModel() {

    override val selectedScreenIndexState = mutableStateOf(AppScreen.INIT_DATA)

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    init {
         //init data here
         selectedScreenIndexState.value = AppScreen.LOGIN
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