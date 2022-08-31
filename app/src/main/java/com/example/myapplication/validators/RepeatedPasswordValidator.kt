package com.example.myapplication.validators

class RepeatedPasswordValidator {
    fun validate(password: String, repeatedPassword: String) : ValidationResult {
        return if(password != repeatedPassword) {
            ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        } else {
            ValidationResult(
                successful = true,
            )
        }
    }
}