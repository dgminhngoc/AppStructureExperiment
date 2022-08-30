package com.example.myapplication.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.screen.App
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.providers.Provider
import com.example.myapplication.screen.AppTest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                AppTest()
            }
        }
    }
}