package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.BottomNavTab
import com.example.myapplication.viewmodel.MainScreenEvent
import com.example.myapplication.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel
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
                        HomePage(
                            homePageViewModel = mainScreenViewModel.homePageViewModel
                        )
                    }
                    BottomNavTab.VIDEOS -> {
                        VideosPage(
                            videosPageViewModel = mainScreenViewModel.videosPageViewModel
                        )
                    }
                    BottomNavTab.PRODUCTS -> {
                        ProductsPage(
                            productsPageViewModel = mainScreenViewModel.productsPageViewModel
                        )
                    }
                    BottomNavTab.CONTACTS -> {
                        ContactsPage(
                            contactsPageViewModel = mainScreenViewModel.contactsPageViewModel
                        )
                    }
                }
            }
        }
    )
}

