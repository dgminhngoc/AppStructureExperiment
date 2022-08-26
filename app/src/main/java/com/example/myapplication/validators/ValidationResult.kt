package com.example.myapplication.validators

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
