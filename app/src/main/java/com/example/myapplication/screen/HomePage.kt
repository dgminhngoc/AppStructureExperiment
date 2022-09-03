package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.viewmodel.BottomNavTab
import com.example.myapplication.viewmodel.HomePageViewModel
import com.example.myapplication.viewmodel.MainScreenViewModel

@Composable
fun HomePage(
    homePageViewModel: HomePageViewModel =
        ViewModels.get(HomePageViewModel::class.java.name),
    mainScreenViewModel: MainScreenViewModel =
        ViewModels.get(MainScreenViewModel::class.java.name),
) {
    val mainScreenSelectedTabIndexState by mainScreenViewModel.selectedTabIndexState.collectAsState()
    DisposableEffect(mainScreenSelectedTabIndexState) {
        onDispose {
            if(mainScreenSelectedTabIndexState != BottomNavTab.HOME) {
                homePageViewModel.dispose()
            }
        }
    }

    Text(text = "Home Page")
}