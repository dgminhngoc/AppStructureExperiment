package com.example.myapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.domain.IServerDataRepository
import com.example.myapplication.domain.RequestResult
import com.example.myapplication.validators.EmailValidator
import kotlinx.coroutines.*

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

abstract class IResetPasswordPageViewModel: IViewModel() {
    abstract val resetPasswordFormState: MutableState<ResetPasswordFormState>
    abstract val resetPasswordSubmittedState: MutableState<Boolean>
    abstract fun onEvent(event: ResetPasswordFormEvent)
}

class ResetPasswordPageViewModel(
    private val dataRepository: IServerDataRepository,
    private val emailValidator: EmailValidator = EmailValidator(),
): IResetPasswordPageViewModel() {

    override val resetPasswordFormState = mutableStateOf(ResetPasswordFormState())
    override val resetPasswordSubmittedState = mutableStateOf(false)

    private var resetPasswordJob: Job? = null
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    override fun onEvent(event: ResetPasswordFormEvent) {
        when(event) {
            is ResetPasswordFormEvent.ResetPasswordFormChanged -> {
                resetPasswordFormState.value = ResetPasswordFormState(
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
            resetPasswordFormState.value = resetPasswordFormState.value.copy(
                emailError = emailValidationResult.errorMessage,
            )

            return
        }

        resetPasswordJob = CoroutineScope(defaultDispatcher).launch {
            when(val result = dataRepository.resetPassword(
                email = resetPasswordFormState.value.email,
            )){
                is RequestResult.OnSuccess -> {
                    result.data?.let {
                        resetPasswordSubmittedState.value = true
                    }

                }
                is RequestResult.OnError -> {
                    resetPasswordSubmittedState.value = false
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        resetPasswordJob?.cancel()
        Log.d("TEST", "RegisterPageViewModel dispose")
    }
}