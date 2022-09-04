package com.example.myapplication.viewmodel

import android.util.Log
import com.example.myapplication.data.IServerDataRepository
import com.example.myapplication.data.RequestResult
import com.example.myapplication.ui.UIString
import com.example.myapplication.validators.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class RegistrationFormEvent {
    data class RegistrationFormChanged(
        val email: String,
        val password: String,
        val isPasswordVisible: Boolean,
        val repeatedPassword: String,
        val isRepeatedPasswordVisible: Boolean,
        val firstName: String,
        val lastName: String,
        val isTermsAccepted: Boolean,
    ): RegistrationFormEvent()

    object RegistrationFormSubmit: RegistrationFormEvent()
}

sealed class RegistrationPageUIState {
    object RequestSending: RegistrationPageUIState()

    data class ResultReceived(
        val requestResult: RequestResult<String>
    ): RegistrationPageUIState()

    object Standard: RegistrationPageUIState()
}

data class RegistrationFormState(
    val firstName: String = "",
    val firstNameError: UIString? = null,
    val lastname: String = "",
    val lastnameError: UIString? = null,
    val email: String = "",
    val emailError: UIString? = null,
    val password: String = "",
    val passwordError: UIString? = null,
    val isPasswordVisible: Boolean = false,
    val repeatedPassword: String = "",
    val repeatedPasswordError: UIString? = null,
    val isRepeatedPasswordVisible: Boolean = false,
    val isTermsAccepted: Boolean = false,
    val isTermsAcceptedError: UIString? = null,
)

abstract class RegistrationPageViewModel: BaseViewModel() {
    abstract val registrationFormState: StateFlow<RegistrationFormState>
    abstract val registrationPageUIState: StateFlow<RegistrationPageUIState>
    abstract fun onEvent(event: RegistrationFormEvent)
}

class RegistrationPageViewModelImpl(
    private val dataRepository: IServerDataRepository,
    private val emailValidator: EmailValidator = EmailValidator(),
    private val passwordValidator: PasswordValidator = PasswordValidator(),
    private val repeatedPasswordValidator: RepeatedPasswordValidator = RepeatedPasswordValidator(),
    private val nameValidator: NameValidator = NameValidator(),
    private val termsAcceptedValidator: TermsAcceptedValidator = TermsAcceptedValidator(),
): RegistrationPageViewModel() {

    private val _registrationFormState = MutableStateFlow(RegistrationFormState())
    override val registrationFormState = _registrationFormState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _registrationFormState.value
    )
    private val _registrationPageUIState = MutableStateFlow<RegistrationPageUIState>(RegistrationPageUIState.Standard)
    override val registrationPageUIState = _registrationPageUIState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _registrationPageUIState.value
    )

    private var registrationJob: Job? = null
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    init {
        Log.d("TEST", "RegisterPageViewModel init")
    }

    override fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.RegistrationFormChanged -> {
                _registrationFormState.value = RegistrationFormState(
                    email = event.email,
                    password = event.password,
                    isPasswordVisible = event.isPasswordVisible,
                    repeatedPassword = event.repeatedPassword,
                    isRepeatedPasswordVisible = event.isRepeatedPasswordVisible,
                    firstName = event.firstName,
                    lastname = event.lastName,
                    isTermsAccepted = event.isTermsAccepted,
                )
            }
            is RegistrationFormEvent.RegistrationFormSubmit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailValidationResult = emailValidator.validate(registrationFormState.value.email)
        val passwordValidationResult = passwordValidator.validate(registrationFormState.value.password)
        val repeatedPasswordValidationResult = repeatedPasswordValidator.validate(
            registrationFormState.value.password,
            registrationFormState.value.repeatedPassword
        )
        val firstNameValidationResult = nameValidator.validate(registrationFormState.value.firstName)
        val lastNameValidationResult = nameValidator.validate(registrationFormState.value.lastname)
        val termsValidationResult = termsAcceptedValidator.validate(registrationFormState.value.isTermsAccepted)

        val hasError = listOf(
            emailValidationResult,
            passwordValidationResult,
            repeatedPasswordValidationResult,
            firstNameValidationResult,
            lastNameValidationResult,
            termsValidationResult
        ).any {validationResult->
            validationResult.errorMessage != null
        }

        if(hasError) {
            _registrationFormState.update { previousState->
                previousState.copy(
                    emailError = emailValidationResult.errorMessage,
                    passwordError = passwordValidationResult.errorMessage,
                    repeatedPasswordError = repeatedPasswordValidationResult.errorMessage,
                    firstNameError = firstNameValidationResult.errorMessage,
                    lastnameError = lastNameValidationResult.errorMessage,
                    isTermsAcceptedError = termsValidationResult.errorMessage,
                )
            }

            return
        }

        _registrationPageUIState.value = RegistrationPageUIState.RequestSending

        registrationJob = CoroutineScope(defaultDispatcher).launch {

            _registrationPageUIState.value = RegistrationPageUIState.ResultReceived(
                requestResult = dataRepository.register(
                    email = registrationFormState.value.email,
                    password = registrationFormState.value.password,
                    firstName = registrationFormState.value.firstName,
                    lastName = registrationFormState.value.lastname,
                    authority = "ROLE_GUEST"
                )
            )
        }
    }

    override fun dispose() {
        super.dispose()

        registrationJob?.cancel()
        Log.d("TEST", "RegisterPageViewModel dispose")
    }
}