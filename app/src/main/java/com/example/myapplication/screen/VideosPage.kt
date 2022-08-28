package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.viewmodel.BottomNavTab
import com.example.myapplication.viewmodel.IMainScreenViewModel
import com.example.myapplication.viewmodel.IVideosPageViewModel
import localProvider

@Composable
fun VideosPage(
    videosPageViewModel: IVideosPageViewModel =
        localProvider.current.getViewModel(IVideosPageViewModel::class.java),
    mainScreenViewModel: IMainScreenViewModel =
        localProvider.current.getViewModel(IMainScreenViewModel::class.java),
) {
    val mainScreenSelectedTabIndexState by mainScreenViewModel.selectedTabIndexState.collectAsState()
    DisposableEffect(mainScreenSelectedTabIndexState) {
        onDispose {
            if(mainScreenSelectedTabIndexState != BottomNavTab.VIDEOS) {
                videosPageViewModel.onCleared()
            }
        }
    }

    Text(text = "Video Screen")
}