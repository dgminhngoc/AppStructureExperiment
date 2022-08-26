package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.ResetPasswordPageViewModel

@Composable
fun ResetPasswordPage(
    viewModel: ResetPasswordPageViewModel = ResetPasswordPageViewModel()
) {
    Text(text = "Reset Password Page")
}