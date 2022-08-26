package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IProductsPageViewModel
import com.example.myapplication.viewmodel.ProductsPageViewModel
import com.example.myapplication.viewmodel.localAppViewModel

@Composable
fun ProductsPage(
    productsPageViewModel: IProductsPageViewModel = localAppViewModel.current.mainScreenViewModel.productsPageViewModel
) {
    Text(text = "Product Page")
}