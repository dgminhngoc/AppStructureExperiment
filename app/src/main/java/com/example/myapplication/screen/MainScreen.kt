package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.BottomNavTab
import com.example.myapplication.viewmodel.MainScreenViewModel

@Preview
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = MainScreenViewModel(),
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
                        selected = viewModel.selectedTabIndexState.value.rank == index,
                        onClick = { viewModel.selectTab(when(index) {
                            0 -> BottomNavTab.HOME
                            1 -> BottomNavTab.VIDEOS
                            2 -> BottomNavTab.PRODUCTS
                            3 -> BottomNavTab.CONTACTS
                            else -> BottomNavTab.HOME
                        }) }
                    )
                }
            }
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                when(viewModel.selectedTabIndexState.value) {
                    BottomNavTab.HOME -> {
                        HomePage(
                            viewModel = viewModel.homePageViewModel
                        )
                    }
                    BottomNavTab.VIDEOS -> {
                        VideosPage(
                            viewModel = viewModel.videosPageViewModel
                        )
                    }
                    BottomNavTab.PRODUCTS -> {
                        ProductsPage(
                            viewModel = viewModel.productsPageViewModel
                        )
                    }
                    BottomNavTab.CONTACTS -> {
                        ContactsPage(
                            viewModel = viewModel.contactsPageViewModel
                        )
                    }
                }
            }
        }
    )
}

