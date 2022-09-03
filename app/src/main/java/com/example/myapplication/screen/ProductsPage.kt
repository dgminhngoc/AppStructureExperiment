package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.viewmodel.BottomNavTab
import com.example.myapplication.viewmodel.MainScreenViewModel
import com.example.myapplication.viewmodel.ProductsPageViewModel

@Composable
fun ProductsPage(
    productsPageViewModel: ProductsPageViewModel =
        ViewModels.get(ProductsPageViewModel::class.java.name),
    mainScreenViewModel: MainScreenViewModel =
        ViewModels.get(MainScreenViewModel::class.java.name),
) {
    val mainScreenSelectedTabIndexState by mainScreenViewModel.selectedTabIndexState.collectAsState()
    DisposableEffect(mainScreenSelectedTabIndexState) {
        onDispose {
            if(mainScreenSelectedTabIndexState != BottomNavTab.PRODUCTS) {
                productsPageViewModel.dispose()
            }
        }
    }

    Text(text = "Product Page")
}