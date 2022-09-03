package com.example.myapplication.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.IViewModel

val LocalViewModelProvider = compositionLocalOf<IViewModelStore> {
    error("com.example.myapplication.providers.IViewModelProvider not set")
}
val LocalResourceProvider = compositionLocalOf<IResources> {
    error("com.example.myapplication.providers.IResourcesProvider not set")
}

val viewModelStore = ViewModelStore()

@Composable
fun LocalProvider(
    viewModelsStore: IViewModelStore = viewModelStore,
    resources: IResources = Resources(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalViewModelProvider provides viewModelsStore,
        LocalResourceProvider provides resources,
    ) {
        MyApplicationTheme {
            content()
        }
    }
}

object ViewModels {
    @Composable
    @ReadOnlyComposable
    fun <T: IViewModel> get(key: String): T {
        return LocalViewModelProvider.current.getViewModel(key)
    }

    @Composable
    @ReadOnlyComposable
    inline fun <reified VM : IViewModel> getViewModel(): VM {
        return LocalViewModelProvider.current.getViewModel(VM::class.java.name)
    }
}