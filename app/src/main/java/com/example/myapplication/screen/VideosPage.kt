package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IVideosPageViewModel
import com.example.myapplication.viewmodel.localAppViewModel

@Composable
fun VideosPage(
    videosPageViewModel: IVideosPageViewModel = localAppViewModel.current.mainScreenViewModel.videosPageViewModel
) {
    Text(text = "Video Screen")
}