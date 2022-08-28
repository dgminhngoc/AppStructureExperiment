package com.example.myapplication.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

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
    abstract val selectedPageIndexState: MutableState<LoginNavigatePage>
    abstract fun onEvent(event: LoginScreenEvent)
}

class LoginScreenViewModel: ILoginScreenViewModel() {

    override val selectedPageIndexState: MutableState<LoginNavigatePage> = mutableStateOf(LoginNavigatePage.LOGIN)

    override fun onEvent(event: LoginScreenEvent) {
        when(event) {
            is LoginScreenEvent.LoginPageNavigate -> {
                selectedPageIndexState.value = event.page
            }
        }
    }
}