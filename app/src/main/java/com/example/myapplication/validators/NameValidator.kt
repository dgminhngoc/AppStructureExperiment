package com.example.myapplication.validators

class NameValidator {
    fun execute(name: String) : ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Your name can not be blank"
            )
        }

        return ValidationResult(
            successful = true,
        )
    }
}