package com.example.myapplication.validators

class TermsAcceptedValidator {
    fun validate(isTermsAccepted: Boolean) : ValidationResult {
        if(!isTermsAccepted) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms"
            )
        }

        return ValidationResult(
            successful = true,
        )
    }
}