package com.example.myapplication.validators

import android.util.Patterns

class EmailValidator {
    fun execute(email: String) : ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The E-Mail can not be blank"
            )
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "E-Mail is not valid"
            )
        }

        return ValidationResult(
            successful = true,
        )
    }
}