package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.providers.getViewModel
import com.example.myapplication.viewmodel.*

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = getViewModel(),
    appViewModel: AppViewModel = getViewModel(),
) {

    val appSelectedScreenIndexState by appViewModel.selectedScreenIndexState.collectAsState()
    DisposableEffect(appSelectedScreenIndexState) {
        onDispose {
            if(appSelectedScreenIndexState != AppScreen.MAIN) {
                mainScreenViewModel.dispose()
            }
        }
    }

    val mainScreenSelectedTabIndexState by mainScreenViewModel.selectedTabIndexState.collectAsState()
    val items = listOf("News", "Videos", "Products", "Contacts")
    Scaffold(
        bottomBar = {
            BottomNavigation (
                elevation = 10.dp
            ){
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(item) },
                        selected = mainScreenSelectedTabIndexState.index == index,
                        onClick = { mainScreenViewModel.onEvent(when(index) {
                            BottomNavTab.HOME.index -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.HOME)
                            BottomNavTab.VIDEOS.index -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.VIDEOS)
                            BottomNavTab.PRODUCTS.index -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.PRODUCTS)
                            BottomNavTab.CONTACTS.index -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.CONTACTS)
                            else -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.HOME)
                        }) }
                    )
                }
            }
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                when(mainScreenSelectedTabIndexState) {
                    BottomNavTab.HOME -> {
                        HomePage()
                    }
                    BottomNavTab.VIDEOS -> {
                        VideosPage()
                    }
                    BottomNavTab.PRODUCTS -> {
                        ProductsPage()
                    }
                    BottomNavTab.CONTACTS -> {
                        ContactsPage()
                    }
                }
            }
        }
    )
}

