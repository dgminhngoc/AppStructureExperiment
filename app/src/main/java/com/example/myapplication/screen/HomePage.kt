package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.HomePageViewModel

@Composable
fun HomePage(
    viewModel: HomePageViewModel = HomePageViewModel()
) {
    Text(text = "Home Page")
}