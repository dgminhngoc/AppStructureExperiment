package com.example.myapplication.validators

import com.example.myapplication.R
import com.example.myapplication.ui.UIString

class TermsAcceptedValidator {
    fun validate(isTermsAccepted: Boolean) : ValidationResult {
        if(!isTermsAccepted) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UIString.DynamicString(
                    resId = R.string.validation_error_terms_not_accepted
                )
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}