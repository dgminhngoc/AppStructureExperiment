package com.example.myapplication.viewmodel

import com.example.myapplication.domain.IUserPreferencesRepository
import com.example.myapplication.models.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

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
    abstract val selectedScreenIndexState: StateFlow<AppScreen>
    abstract fun onUIEvent(event: UIEvent)
}

class AppViewModel(
    private val prefsRepository: IUserPreferencesRepository,
): IAppViewModel() {

    private val _selectedScreenIndexState = MutableStateFlow(AppScreen.INIT_DATA)
    override val selectedScreenIndexState= _selectedScreenIndexState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _selectedScreenIndexState.value
    )
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    init {
         //init data here
         _selectedScreenIndexState.value = AppScreen.LOGIN
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

        _selectedScreenIndexState.value = AppScreen.MAIN
    }

    private fun logout() {
        _selectedScreenIndexState.value = AppScreen.LOGIN
    }
}