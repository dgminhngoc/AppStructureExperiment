package com.example.myapplication.viewmodel

import android.util.Log
import com.example.myapplication.data.IServerDataRepository
import com.example.myapplication.data.RequestResult
import com.example.myapplication.validators.EmailValidator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class ResetPasswordFormEvent {
    data class ResetPasswordFormChanged(
        val email: String,
    ): ResetPasswordFormEvent()

    object ResetPasswordFormSubmit: ResetPasswordFormEvent()
}

sealed class ResetPasswordPageUIState {
    object RequestSending: ResetPasswordPageUIState()
    data class ResultReceived(
        val requestResult: RequestResult<String>
    ): ResetPasswordPageUIState()
    object Standard: ResetPasswordPageUIState()
}

data class ResetPasswordFormState(
    val email: String = "",
    val emailError: String? = null,
)

abstract class ResetPasswordPageViewModel: BaseViewModel() {
    abstract val resetPasswordFormState: StateFlow<ResetPasswordFormState>
    abstract val resetPasswordPageUIState: StateFlow<ResetPasswordPageUIState>
    abstract fun onEvent(event: ResetPasswordFormEvent)
}

class ResetPasswordPageViewModelImpl(
    private val dataRepository: IServerDataRepository,
    private val emailValidator: EmailValidator = EmailValidator(),
): ResetPasswordPageViewModel() {

    private val _resetPasswordFormState = MutableStateFlow(ResetPasswordFormState())
    override val resetPasswordFormState = _resetPasswordFormState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _resetPasswordFormState.value
    )
    private val _resetPasswordPageUIState = MutableStateFlow<ResetPasswordPageUIState>(ResetPasswordPageUIState.Standard)
    override val resetPasswordPageUIState = _resetPasswordPageUIState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _resetPasswordPageUIState.value
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
        val emailValidationResult = emailValidator.validate(resetPasswordFormState.value.email)

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

        _resetPasswordPageUIState.value = ResetPasswordPageUIState.RequestSending
        resetPasswordJob = CoroutineScope(defaultDispatcher).launch {
            _resetPasswordPageUIState.value = ResetPasswordPageUIState.ResultReceived(
                requestResult = dataRepository.resetPassword(
                    email = resetPasswordFormState.value.email,
                )
            )
        }
    }

    override fun dispose() {
        super.dispose()

        resetPasswordJob?.cancel()
        Log.d("TEST", "RegisterPageViewModel dispose")
    }
}