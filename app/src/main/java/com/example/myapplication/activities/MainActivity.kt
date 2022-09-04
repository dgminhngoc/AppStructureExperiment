package com.example.myapplication.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.myapplication.providers.*
import com.example.myapplication.screen.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivityViewModel: MainActivityViewModel by viewModels()
        val viewModelStore = mainActivityViewModel.getViewModelStore()
        setContent {
            LocalProvider(
                viewModelStore = viewModelStore,
                resources = Resources()
            ) {
                App()
            }
        }
    }
}

class MainActivityViewModel: IViewModelStoreOwner, ViewModel() {

    private val viewModelStore = ViewModelStoreImpl()

    override fun getViewModelStore(): IViewModelStore = viewModelStore
}