package com.example.myapplication.validators

import com.example.myapplication.R
import com.example.myapplication.ui.UIString

class PasswordValidator {
    fun validate(password: String) : ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UIString.DynamicString(
                    resId = R.string.validation_error_password_length
                )
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}