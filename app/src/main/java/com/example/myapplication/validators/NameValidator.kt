package com.example.myapplication.validators

import com.example.myapplication.R
import com.example.myapplication.ui.UIString

class NameValidator {
    fun validate(name: String) : ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UIString.DynamicString(
                    resId = R.string.validation_error_name_blank
                )
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}