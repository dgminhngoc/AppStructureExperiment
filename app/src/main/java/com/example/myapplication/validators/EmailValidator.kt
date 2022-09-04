package com.example.myapplication.validators

import android.util.Patterns
import com.example.myapplication.R
import com.example.myapplication.ui.UIString

class EmailValidator {
    fun validate(email: String) : ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UIString.DynamicString(
                    resId = R.string.validation_error_email_blank
                )
            )
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UIString.DynamicString(
                    resId = R.string.validation_error_email_not_valid
                )
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}