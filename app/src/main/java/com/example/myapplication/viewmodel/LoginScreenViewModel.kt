package com.example.myapplication.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.domain.IDataRepository

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

class LoginScreenViewModel(
    private val dataRepository: IDataRepository
): ILoginScreenViewModel() {

    override val selectedPageIndexState: MutableState<LoginNavigatePage> = mutableStateOf(LoginNavigatePage.LOGIN)

    private var _loginPageViewModel: ILoginPageViewModel? = null
    val loginPageViewModel: ILoginPageViewModel
        get() {
            _loginPageViewModel = _loginPageViewModel ?: LoginPageViewModel(dataRepository)
            return _loginPageViewModel!!
        }

    private var _registrationPageViewModel: IRegistrationPageViewModel? = null
    val registrationPageViewModel: IRegistrationPageViewModel
        get() {
            _registrationPageViewModel = _registrationPageViewModel ?: RegistrationPageViewModel(dataRepository)
            return _registrationPageViewModel!!
        }

    private var _resetPasswordPageViewModel: IResetPasswordPageViewModel? = null
    val resetPasswordPageViewModel: IResetPasswordPageViewModel
        get() {
            _resetPasswordPageViewModel = _resetPasswordPageViewModel ?: ResetPasswordPageViewModel(dataRepository)
            return _resetPasswordPageViewModel!!
        }

    override fun onEvent(event: LoginScreenEvent) {
        when(event) {
            is LoginScreenEvent.LoginPageNavigate -> {
                selectedPageIndexState.value = event.page
                disposePageViewModel(selectedPageIndexState.value)
            }
        }
    }

    override fun dispose() {
        disposePageViewModel(LoginNavigatePage.LOGIN)
        disposePageViewModel(LoginNavigatePage.REGISTER)
        disposePageViewModel(LoginNavigatePage.RESET_PASSWORD)
    }

    private fun disposePageViewModel(page: LoginNavigatePage) {
        when(page) {
            LoginNavigatePage.LOGIN -> {
                _loginPageViewModel?.dispose()
                _loginPageViewModel = null
            }
            LoginNavigatePage.REGISTER -> {
                _registrationPageViewModel?.dispose()
                _registrationPageViewModel = null
            }
            LoginNavigatePage.RESET_PASSWORD -> {
                _resetPasswordPageViewModel?.dispose()
                _resetPasswordPageViewModel = null
            }
        }
    }
}