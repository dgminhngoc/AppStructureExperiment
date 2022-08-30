package com.example.myapplication.viewmodel

import android.util.Log
import com.example.myapplication.domain.IServerDataRepository
import com.example.myapplication.domain.RequestResult
import com.example.myapplication.validators.EmailValidator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class ResetPasswordFormEvent {
    data class ResetPasswordFormChanged(
        val email: String,
    ): ResetPasswordFormEvent()

    object ResetPasswordFormSubmit: ResetPasswordFormEvent()
}

data class ResetPasswordFormState(
    val email: String = "",
    val emailError: String? = null,
)

interface IResetPasswordPageViewModel: IViewModel {
    val resetPasswordFormState: StateFlow<ResetPasswordFormState>
    val resetPasswordSubmittedState: StateFlow<Boolean>
    fun onEvent(event: ResetPasswordFormEvent)
}

class ResetPasswordPageViewModel(
    private val dataRepository: IServerDataRepository,
    private val emailValidator: EmailValidator = EmailValidator(),
    onDisposeAction: (() -> Unit)? = null
): IResetPasswordPageViewModel, BaseViewModel(onDisposeAction = onDisposeAction) {

    private val _resetPasswordFormState = MutableStateFlow(ResetPasswordFormState())
    override val resetPasswordFormState = _resetPasswordFormState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _resetPasswordFormState.value
    )
    private val _resetPasswordSubmittedState = MutableStateFlow(false)
    override val resetPasswordSubmittedState = _resetPasswordSubmittedState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _resetPasswordSubmittedState.value
    )

    private var resetPasswordJob: Job? = null
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    override fun onEvent(event: ResetPasswordFormEvent) {
        when(event) {
            is ResetPasswordFormEvent.ResetPasswordFormChanged -> {
                _resetPasswordFormState.value = ResetPasswordFormState(
                    email = event.email,
                )
            }
            is ResetPasswordFormEvent.ResetPasswordFormSubmit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailValidationResult = emailValidator.execute(resetPasswordFormState.value.email)

        val hasError = listOf(
            emailValidationResult,
        ).any {validationResult->
            validationResult.errorMessage != null
        }

        if(hasError) {
            _resetPasswordFormState.update{ previousState->
                previousState.copy(
                    emailError = emailValidationResult.errorMessage,
                )
            }

            return
        }

        resetPasswordJob = CoroutineScope(defaultDispatcher).launch {
            when(val result = dataRepository.resetPassword(
                email = resetPasswordFormState.value.email,
            )){
                is RequestResult.OnSuccess -> {
                    result.data?.let {
                        _resetPasswordSubmittedState.value = true
                    }

                }
                is RequestResult.OnError -> {
                    _resetPasswordSubmittedState.value = false
                }
            }
        }
    }

    override fun dispose() {
        super.dispose()

        resetPasswordJob?.cancel()
        Log.d("TEST", "RegisterPageViewModel dispose")
    }
}