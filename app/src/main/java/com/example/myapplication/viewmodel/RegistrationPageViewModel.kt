package com.example.myapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.domain.IServerDataRepository
import com.example.myapplication.domain.RequestResult
import com.example.myapplication.validators.*
import kotlinx.coroutines.*

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

abstract class IRegistrationPageViewModel: IViewModel() {
    abstract val registrationFormState: MutableState<RegistrationFormState>
    abstract val registrationSubmittedState: MutableState<Boolean>
    abstract fun onEvent(event: RegistrationFormEvent)
}

class RegistrationPageViewModel(
    private val dataRepository: IServerDataRepository,
    private val emailValidator: EmailValidator = EmailValidator(),
    private val passwordValidator: PasswordValidator = PasswordValidator(),
    private val repeatedPasswordValidator: RepeatedPasswordValidator = RepeatedPasswordValidator(),
    private val nameValidator: NameValidator = NameValidator(),
    private val termsValidator: TermsValidator = TermsValidator(),
): IRegistrationPageViewModel() {

    override val registrationFormState = mutableStateOf(RegistrationFormState())
    override val registrationSubmittedState = mutableStateOf(false)

    private var registrationJob: Job? = null
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    init {
        Log.d("TEST", "RegisterPageViewModel init")

    }

    override fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.RegistrationFormChanged -> {
                registrationFormState.value = RegistrationFormState(
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
        val emailValidationResult = emailValidator.execute(registrationFormState.value.email)
        val passwordValidationResult = passwordValidator.execute(registrationFormState.value.password)
        val repeatedPasswordValidationResult = repeatedPasswordValidator.execute(
            registrationFormState.value.password,
            registrationFormState.value.repeatedPassword
        )
        val firstNameValidationResult = nameValidator.execute(registrationFormState.value.firstName)
        val lastNameValidationResult = nameValidator.execute(registrationFormState.value.lastname)
        val termsValidationResult = termsValidator.execute(registrationFormState.value.isTermsAccepted)

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
            registrationFormState.value = registrationFormState.value.copy(
                emailError = emailValidationResult.errorMessage,
                passwordError = passwordValidationResult.errorMessage,
                repeatedPasswordError = repeatedPasswordValidationResult.errorMessage,
                firstNameError = firstNameValidationResult.errorMessage,
                lastnameError = lastNameValidationResult.errorMessage,
                isTermsAcceptedError = termsValidationResult.errorMessage,
            )

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
                        registrationSubmittedState.value = true
                    }

                }
                is RequestResult.OnError -> {
                    registrationSubmittedState.value = false
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        registrationJob?.cancel()
        Log.d("TEST", "RegisterPageViewModel dispose")
    }
}