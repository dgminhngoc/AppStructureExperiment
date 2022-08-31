package com.example.myapplication.validators

class RepeatedPasswordValidator {
    fun validate(password: String, repeatedPassword: String) : ValidationResult {
        return if(password != repeatedPassword) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "The passwords don't match"
            )
        } else {
            ValidationResult(
                isSuccessful = true,
            )
        }
    }
}