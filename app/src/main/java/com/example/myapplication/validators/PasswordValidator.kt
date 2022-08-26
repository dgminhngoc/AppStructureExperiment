package com.example.myapplication.validators

class PasswordValidator {
    fun execute(password: String) : ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The Password must consist of at least 8 characters"
            )
        }

        return ValidationResult(
            successful = true,
        )
    }
}