package com.example.myapplication.screen

import Provider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import localProvider

@Preview(
    widthDp = 300,
    heightDp = 500
)
@Composable
fun LoginPagePreview() {
    val provider = Provider()
    CompositionLocalProvider(localProvider provides provider) {
        LoginPage()
    }
}