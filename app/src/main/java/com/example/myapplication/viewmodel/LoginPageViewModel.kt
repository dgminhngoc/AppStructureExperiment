package com.example.myapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.domain.IDataRepository
import com.example.myapplication.domain.RequestResult
import com.example.myapplication.models.User
import com.example.myapplication.validators.EmailValidator
import com.example.myapplication.validators.PasswordValidator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class LoginFormEvent {
    data class LoginFormChanged(
        val email: String,
        val password: String,
    ): LoginFormEvent()

    object LoginFormSubmit: LoginFormEvent()
}

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)

data class LoginFormSubmittedState(
    val isSuccessful: Boolean = false,
    val user: User? = null,
)

abstract class ILoginPageViewModel: IViewModel() {
    abstract val loginFormState: StateFlow<LoginFormState>
    abstract val loginFormSubmittedState: StateFlow<LoginFormSubmittedState>
    abstract fun onEvent(event: LoginFormEvent)
}

class LoginPageViewModel(
    private val dataRepository: IDataRepository,
    private val emailValidator: EmailValidator = EmailValidator(),
    private val passwordValidator: PasswordValidator = PasswordValidator(),
): ILoginPageViewModel() {

    private val _loginFormState = MutableStateFlow(LoginFormState())
    override val loginFormState = _loginFormState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _loginFormState.value
    )
    private val _loginFormSubmittedState = MutableStateFlow(LoginFormSubmittedState())
    override val loginFormSubmittedState = _loginFormSubmittedState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _loginFormSubmittedState.value
    )

    private var loginJob: Job? = null
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    override fun onEvent(event: LoginFormEvent) {
        when(event) {
            is LoginFormEvent.LoginFormChanged -> {
                _loginFormState.value = LoginFormState(
                    email = event.email,
                    password = event.password,
                )
            }
            is LoginFormEvent.LoginFormSubmit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailValidationResult = emailValidator.execute(_loginFormState.value.email)
        val passwordValidationResult = passwordValidator.execute(_loginFormState.value.password)

        val hasError = listOf(
            emailValidationResult,
            passwordValidationResult,
        ).any {validationResult->
            validationResult.errorMessage != null
        }

        if(hasError) {
            _loginFormState.value = _loginFormState.value.copy(
                emailError = emailValidationResult.errorMessage,
                passwordError = passwordValidationResult.errorMessage,
            )

            return
        }

        loginJob = CoroutineScope(defaultDispatcher).launch {
            when(val result = dataRepository.authenticate(
                email = _loginFormState.value.email,
                password = _loginFormState.value.password
            )){
                is RequestResult.OnSuccess -> {
                    result.data?.let {
                        _loginFormSubmittedState.value = LoginFormSubmittedState(
                            isSuccessful = true,
                            user = it
                        )
                    }

                }
                is RequestResult.OnError -> {
                    _loginFormSubmittedState.value = LoginFormSubmittedState(
                        isSuccessful = false,
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        loginJob?.cancel()
        loginJob = null

        Log.d("TEST", "LoginPageViewModel onCleared")
    }
}