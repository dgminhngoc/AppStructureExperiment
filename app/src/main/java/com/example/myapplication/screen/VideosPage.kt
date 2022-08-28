package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IVideosPageViewModel
import com.example.myapplication.viewmodel.localViewModelProvider

@Composable
fun VideosPage(
    videosPageViewModel: IVideosPageViewModel = localViewModelProvider.current.getViewModel(IVideosPageViewModel::class.java),
) {
    Text(text = "Video Screen")
}