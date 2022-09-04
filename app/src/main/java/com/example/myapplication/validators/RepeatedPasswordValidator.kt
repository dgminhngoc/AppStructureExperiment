package com.example.myapplication.validators

import com.example.myapplication.R
import com.example.myapplication.ui.UIString

class RepeatedPasswordValidator {
    fun validate(password: String, repeatedPassword: String) : ValidationResult {
        return if(password != repeatedPassword) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = UIString.DynamicString(
                    resId = R.string.validation_error_passwords_dont_match
                )
            )
        } else {
            ValidationResult(
                isSuccessful = true,
            )
        }
    }
}