package com.example.myapplication.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.BaseViewModel

val LocalViewModelProvider = compositionLocalOf<IViewModelStore> {
    error("com.example.myapplication.providers.IViewModelProvider not set")
}
val LocalResourceProvider = compositionLocalOf<IResources> {
    error("com.example.myapplication.providers.IResourcesProvider not set")
}


@Composable
fun LocalProvider(
    viewModelStore: IViewModelStore,
    resources: IResources,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalViewModelProvider provides viewModelStore,
        LocalResourceProvider provides resources,
    ) {
        MyApplicationTheme {
            content()
        }
    }
}

@Composable
@ReadOnlyComposable
inline fun <reified VM : BaseViewModel> getViewModel(): VM {
    return LocalViewModelProvider.current.getViewModel(VM::class.java.name)
}
