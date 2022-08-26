package com.example.myapplication.models

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val accessToken: String,
    val refreshToken: String,
    val userRole: String,
)