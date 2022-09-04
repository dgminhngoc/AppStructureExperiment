package com.example.myapplication.viewmodel

import android.util.Log
import com.example.myapplication.data.IServerDataRepository
import com.example.myapplication.data.RequestResult
import com.example.myapplication.models.User
import com.example.myapplication.ui.UIString
import com.example.myapplication.validators.EmailValidator
import com.example.myapplication.validators.PasswordValidator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class LoginFormEvent {
    data class LoginFormChanged(
        val email: String,
        val password: String,
        val isPasswordVisible: Boolean
    ): LoginFormEvent()

    object LoginFormSubmit: LoginFormEvent()
}

sealed class LoginPageUIState {
    object RequestSending: LoginPageUIState()

    data class ResultReceived(
        val requestResult: RequestResult<User>
    ): LoginPageUIState()

    object Standard: LoginPageUIState()
}

data class LoginFormState(
    val email: String = "",
    val emailError: UIString? = null,
    val password: String = "",
    val passwordError: UIString? = null,
    val isPasswordVisible: Boolean = false
)

abstract class LoginPageViewModel: BaseViewModel() {
    abstract val loginFormState: StateFlow<LoginFormState>
    abstract val loginPageUIState: StateFlow<LoginPageUIState>
    abstract fun onEvent(event: LoginFormEvent)
}

class LoginPageViewModelImpl(
    private val dataRepository: IServerDataRepository,
    private val emailValidator: EmailValidator = EmailValidator(),
    private val passwordValidator: PasswordValidator = PasswordValidator(),
): LoginPageViewModel(){

    private val _loginFormState = MutableStateFlow(LoginFormState())
    override val loginFormState = _loginFormState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _loginFormState.value
    )
    private val _loginPageUIState = MutableStateFlow<LoginPageUIState>(LoginPageUIState.Standard)
    override val loginPageUIState = _loginPageUIState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _loginPageUIState.value
    )

    private var loginJob: Job? = null
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    override fun onEvent(event: LoginFormEvent) {
        when(event) {
            is LoginFormEvent.LoginFormChanged -> {
                _loginFormState.value = LoginFormState(
                    email = event.email,
                    password = event.password,
                    isPasswordVisible = event.isPasswordVisible
                )
            }
            is LoginFormEvent.LoginFormSubmit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailValidationResult = emailValidator.validate(_loginFormState.value.email)
        val passwordValidationResult = passwordValidator.validate(_loginFormState.value.password)

        val hasError = listOf(
            emailValidationResult,
            passwordValidationResult,
        ).any {validationResult->
            validationResult.errorMessage != null
        }

        if(hasError) {
            _loginFormState.update { previousState->
                previousState.copy(
                    emailError = emailValidationResult.errorMessage,
                    passwordError = passwordValidationResult.errorMessage,
                )
            }

            return
        }

        _loginPageUIState.value = LoginPageUIState.RequestSending
        loginJob = CoroutineScope(defaultDispatcher).launch {
            delay(5000)
            _loginPageUIState.value = LoginPageUIState.ResultReceived(
                requestResult = dataRepository.authenticate(
                    email = _loginFormState.value.email,
                    password = _loginFormState.value.password
                )
            )
        }
    }

    override fun dispose() {
        super.dispose()

        loginJob?.cancel()
        loginJob = null

        Log.d("TEST", "LoginPageViewModel onCleared")
    }
}