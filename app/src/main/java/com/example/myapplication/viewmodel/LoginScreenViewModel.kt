package com.example.myapplication.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

sealed class LoginScreenEvent {
    data class LoginPageNavigate(
        val page: LoginNavigatePage
    ): LoginScreenEvent()
}

enum class LoginNavigatePage {
    LOGIN,
    REGISTER,
    RESET_PASSWORD,
}

abstract class ILoginScreenViewModel: IViewModel() {
    abstract val selectedPageIndexState: StateFlow<LoginNavigatePage>
    abstract fun onEvent(event: LoginScreenEvent)
}

class LoginScreenViewModel: ILoginScreenViewModel() {

    private val _selectedPageIndexState = MutableStateFlow(LoginNavigatePage.LOGIN)
    override val selectedPageIndexState= _selectedPageIndexState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _selectedPageIndexState.value
    )

    override fun onEvent(event: LoginScreenEvent) {
        when(event) {
            is LoginScreenEvent.LoginPageNavigate -> {
                _selectedPageIndexState.value = event.page
            }
        }
    }
}