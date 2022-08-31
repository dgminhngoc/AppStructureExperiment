package com.example.myapplication.validators

import android.util.Patterns

class EmailValidator {
    fun validate(email: String) : ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "The E-Mail can not be blank"
            )
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "E-Mail is not valid"
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}