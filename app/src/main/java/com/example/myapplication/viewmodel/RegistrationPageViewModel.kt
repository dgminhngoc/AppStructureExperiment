package com.example.myapplication.viewmodel

import android.util.Log
import com.example.myapplication.domain.IServerDataRepository
import com.example.myapplication.domain.RequestResult
import com.example.myapplication.validators.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class RegistrationFormEvent {
    data class RegistrationFormChanged(
        val email: String,
        val password: String,
        val repeatedPassword: String,
        val firstName: String,
        val lastName: String,
        val isTermsAccepted: Boolean,
    ): RegistrationFormEvent()

    object RegistrationFormSubmit: RegistrationFormEvent()
}

data class RegistrationFormState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastname: String = "",
    val lastnameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val isTermsAccepted: Boolean = false,
    val isTermsAcceptedError: String? = null,
)

interface IRegistrationPageViewModel: IViewModel {
    val registrationFormState: StateFlow<RegistrationFormState>
    val registrationSubmittedState: StateFlow<Boolean>
    fun onEvent(event: RegistrationFormEvent)
}

class RegistrationPageViewModel(
    private val dataRepository: IServerDataRepository,
    private val emailValidator: EmailValidator = EmailValidator(),
    private val passwordValidator: PasswordValidator = PasswordValidator(),
    private val repeatedPasswordValidator: RepeatedPasswordValidator = RepeatedPasswordValidator(),
    private val nameValidator: NameValidator = NameValidator(),
    private val termsAcceptedValidator: TermsAcceptedValidator = TermsAcceptedValidator(),
    onDisposeAction: (() -> Unit)? = null
): IRegistrationPageViewModel, BaseViewModel(onDisposeAction = onDisposeAction) {

    private val _registrationFormState = MutableStateFlow(RegistrationFormState())
    override val registrationFormState = _registrationFormState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _registrationFormState.value
    )
    private val _registrationSubmittedState = MutableStateFlow(false)
    override val registrationSubmittedState = _registrationSubmittedState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _registrationSubmittedState.value
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
                    repeatedPassword = event.repeatedPassword,
                    firstName = event.firstName,
                    lastname = event.lastName,
                    isTermsAccepted = event.isTermsAccepted
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

        registrationJob = CoroutineScope(defaultDispatcher).launch {
            when(val result = dataRepository.register(
                email = registrationFormState.value.email,
                password = registrationFormState.value.password,
                firstName = registrationFormState.value.firstName,
                lastName = registrationFormState.value.lastname,
                authority = "ROLE_GUEST"
            )){
                is RequestResult.OnSuccess -> {
                    result.data?.let {
                        _registrationSubmittedState.value = true
                    }

                }
                is RequestResult.OnError -> {
                    _registrationSubmittedState.value = false
                }
            }
        }
    }

    override fun dispose() {
        super.dispose()

        registrationJob?.cancel()
        Log.d("TEST", "RegisterPageViewModel dispose")
    }
}