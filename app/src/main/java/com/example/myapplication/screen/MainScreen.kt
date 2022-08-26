package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.*

@Composable
fun MainScreen(
    mainScreenViewModel: IMainScreenViewModel = localAppViewModel.current.mainScreenViewModel
) {
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
                        selected = mainScreenViewModel.selectedTabIndexState.value.index == index,
                        onClick = { mainScreenViewModel.onEvent(when(index) {
                            0 -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.HOME)
                            1 -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.VIDEOS)
                            2 -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.PRODUCTS)
                            3 -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.CONTACTS)
                            else -> MainScreenEvent.MainBottomTabNavigate(tab = BottomNavTab.HOME)
                        }) }
                    )
                }
            }
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                when(mainScreenViewModel.selectedTabIndexState.value) {
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

