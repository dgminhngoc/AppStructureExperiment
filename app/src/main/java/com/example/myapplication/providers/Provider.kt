package com.example.myapplication.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.viewmodel.BaseViewModel
import com.example.myapplication.viewmodel.IViewModel

val LocalViewModelProvider = compositionLocalOf<IViewModelProvider> { error("com.example.myapplication.providers.IViewModelProvider not set") }
val LocalResourceProvider = compositionLocalOf<IResourcesProvider> { error("com.example.myapplication.providers.IResourcesProvider not set") }

val viewModelProvider = ViewModelProvider()

@Composable
fun Provider(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalViewModelProvider provides viewModelProvider,
        LocalResourceProvider provides ResourcesProvider(),
    ) {
        content()
    }
}

object ViewModels {
    @Composable
    @ReadOnlyComposable
    fun <T: IViewModel> get(key: String): T {
        return LocalViewModelProvider.current.getViewModel(key)
    }
}