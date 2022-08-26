package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.VideosPageViewModel

@Composable
fun VideosPage(
    viewModel: VideosPageViewModel = VideosPageViewModel()
) {
    Text(text = "Video Screen")
}