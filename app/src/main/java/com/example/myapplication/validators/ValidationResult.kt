package com.example.myapplication.validators

import com.example.myapplication.ui.UIString

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: UIString? = null
)