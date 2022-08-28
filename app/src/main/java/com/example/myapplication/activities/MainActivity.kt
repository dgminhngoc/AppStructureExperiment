package com.example.myapplication.activities

import Provider
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.myapplication.screen.App
import com.example.myapplication.ui.theme.MyApplicationTheme
import localProvider

val provider = Provider()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(localProvider provides provider) {
                    App()
                }
            }
        }
    }
}