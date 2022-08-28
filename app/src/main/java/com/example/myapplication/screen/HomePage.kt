package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IHomePageViewModel
import com.example.myapplication.viewmodel.localViewModelProvider

@Composable
fun HomePage(
    homePageViewModel: IHomePageViewModel = localViewModelProvider.current.getViewModel(IHomePageViewModel::class.java),
) {
    Text(text = "Home Page")
}