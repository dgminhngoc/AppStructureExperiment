package com.example.myapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.domain.IDataRepository

enum class LoginPage(val rank: Int) {
    LOGIN(0),
    REGISTER(1),
    RESET_PASSWORD(2),
}

val localLoginScreenViewModel = compositionLocalOf<LoginScreenViewModel> { error("LoginScreenViewModel not set") }

class LoginScreenViewModel(
    private val dataRepository: IDataRepository
): IViewModel() {

    val selectedPageIndexState: MutableState<LoginPage> = mutableStateOf(LoginPage.LOGIN)

    private var _loginPageViewModel: LoginPageViewModel? = null
    val loginPageViewModel: LoginPageViewModel
        get() {
            _loginPageViewModel = _loginPageViewModel ?: LoginPageViewModel()
            return _loginPageViewModel!!
        }

    private var _registerPageViewModel: RegisterPageViewModel? = null
    val registerPageViewModel: RegisterPageViewModel
        get() {
            _registerPageViewModel = _registerPageViewModel ?: RegisterPageViewModel()
            return _registerPageViewModel!!
        }

    private var _resetPasswordPageViewModel: ResetPasswordPageViewModel? = null
    val resetPasswordPageViewModel: ResetPasswordPageViewModel
        get() {
            _resetPasswordPageViewModel = _resetPasswordPageViewModel ?: ResetPasswordPageViewModel()
            return _resetPasswordPageViewModel!!
        }

    fun selectPage(page: LoginPage) {
        disposePageViewModel(selectedPageIndexState.value)

        selectedPageIndexState.value = page
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
                _registerPageViewModel?.dispose()
                _registerPageViewModel = null
            }
            LoginPage.RESET_PASSWORD -> {
                _resetPasswordPageViewModel?.dispose()
                _resetPasswordPageViewModel = null
            }
        }
    }
}