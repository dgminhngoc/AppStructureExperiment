package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.ProductsPageViewModel

@Composable
fun ProductsPage(
    viewModel: ProductsPageViewModel = ProductsPageViewModel()
) {
    Text(text = "Product Page")
}