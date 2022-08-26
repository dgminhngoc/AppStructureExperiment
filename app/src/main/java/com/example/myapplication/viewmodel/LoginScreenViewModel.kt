package com.example.myapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.domain.IDataRepository

sealed class LoginScreenEvent {
    data class LoginPageNavigate(
        val page: LoginPage
    ): LoginScreenEvent()
}

enum class LoginPage {
    LOGIN,
    REGISTER,
    RESET_PASSWORD,
}

val localLoginScreenViewModel = compositionLocalOf<LoginScreenViewModel> { error("LoginScreenViewModel not set") }

class LoginScreenViewModel(
    private val dataRepository: IDataRepository
): IViewModel() {

    val selectedPageIndexState: MutableState<LoginPage> = mutableStateOf(LoginPage.LOGIN)

    private var _loginPageViewModel: LoginPageViewModel? = null
    val loginPageViewModel: LoginPageViewModel
        get() {
            _loginPageViewModel = _loginPageViewModel ?: LoginPageViewModel(dataRepository)
            return _loginPageViewModel!!
        }

    private var _registrationPageViewModel: RegistrationPageViewModel? = null
    val registrationPageViewModel: RegistrationPageViewModel
        get() {
            _registrationPageViewModel = _registrationPageViewModel ?: RegistrationPageViewModel(dataRepository)
            return _registrationPageViewModel!!
        }

    private var _resetPasswordPageViewModel: ResetPasswordPageViewModel? = null
    val resetPasswordPageViewModel: ResetPasswordPageViewModel
        get() {
            _resetPasswordPageViewModel = _resetPasswordPageViewModel ?: ResetPasswordPageViewModel(dataRepository)
            return _resetPasswordPageViewModel!!
        }

    fun onEvent(event: LoginScreenEvent) {
        when(event) {
            is LoginScreenEvent.LoginPageNavigate -> {
                selectedPageIndexState.value = event.page
                disposePageViewModel(selectedPageIndexState.value)
            }
        }
    }

    override fun dispose() {
        disposePageViewModel(LoginPage.LOGIN)
        disposePageViewModel(LoginPage.REGISTER)
        disposePageViewModel(LoginPage.RESET_PASSWORD)

        Log.d("TEST", "LoginScreenViewModel dispose")
    }

    private fun disposePageViewModel(page: LoginPage) {
        when(page) {
            LoginPage.LOGIN -> {
                _loginPageViewModel?.dispose()
                _loginPageViewModel = null
            }
            LoginPage.REGISTER -> {
                _registrationPageViewModel?.dispose()
                _registrationPageViewModel = null
            }
            LoginPage.RESET_PASSWORD -> {
                _resetPasswordPageViewModel?.dispose()
                _resetPasswordPageViewModel = null
            }
        }
    }
}