package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IHomePageViewModel
import com.example.myapplication.viewmodel.localAppViewModel

@Composable
fun HomePage(
    homePageViewModel: IHomePageViewModel = localAppViewModel.current.mainScreenViewModel.homePageViewModel
) {
    Text(text = "Home Page")
}