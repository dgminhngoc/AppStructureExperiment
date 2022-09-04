package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.providers.getViewModel
import com.example.myapplication.viewmodel.BottomNavTab
import com.example.myapplication.viewmodel.ContactsPageViewModel
import com.example.myapplication.viewmodel.MainScreenViewModel

@Composable
fun ContactsPage(
    contactsPageViewModel: ContactsPageViewModel = getViewModel(),
    mainScreenViewModel: MainScreenViewModel = getViewModel(),
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