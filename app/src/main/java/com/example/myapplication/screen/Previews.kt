package com.example.myapplication.screen

import com.example.myapplication.providers.ViewModelProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.providers.LocalViewModelProvider

@Preview(
    widthDp = 300,
    heightDp = 500
)
@Composable
fun LoginPagePreview() {
    val provider = ViewModelProvider()
    CompositionLocalProvider(LocalViewModelProvider provides provider) {
        LoginPage()
    }
}