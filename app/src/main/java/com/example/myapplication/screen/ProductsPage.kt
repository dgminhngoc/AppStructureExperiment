package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IProductsPageViewModel
import localProvider

@Composable
fun ProductsPage(
    productsPageViewModel: IProductsPageViewModel = localProvider.current.getViewModel(IProductsPageViewModel::class.java),
) {
    Text(text = "Product Page")
}