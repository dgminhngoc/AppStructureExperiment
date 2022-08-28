package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IProductsPageViewModel
import com.example.myapplication.viewmodel.localViewModelProvider

@Composable
fun ProductsPage(
    productsPageViewModel: IProductsPageViewModel = localViewModelProvider.current.getViewModel(IProductsPageViewModel::class.java),
) {
    Text(text = "Product Page")
}