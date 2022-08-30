package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.viewmodel.BottomNavTab
import com.example.myapplication.viewmodel.IContactsPageViewModel
import com.example.myapplication.viewmodel.IMainScreenViewModel

@Composable
fun ContactsPage(
    contactsPageViewModel: IContactsPageViewModel =
        ViewModels.get(IContactsPageViewModel::class.java.name),
    mainScreenViewModel: IMainScreenViewModel =
        ViewModels.get(IMainScreenViewModel::class.java.name),
) {
    val mainScreenSelectedTabIndexState by mainScreenViewModel.selectedTabIndexState.collectAsState()
    DisposableEffect(mainScreenSelectedTabIndexState) {
        onDispose {
            if(mainScreenSelectedTabIndexState != BottomNavTab.CONTACTS) {
                contactsPageViewModel.dispose()
            }
        }
    }
    Text(text = "Contact Screen")
}