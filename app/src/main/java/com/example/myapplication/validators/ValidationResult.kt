package com.example.myapplication.validators

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)