package com.example.myapplication.validators

class PasswordValidator {
    fun validate(password: String) : ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "The Password must consist of at least 8 characters"
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}