package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IContactsPageViewModel
import com.example.myapplication.viewmodel.localAppViewModel

@Composable
fun ContactsPage(
    contactsPageViewModel: IContactsPageViewModel = localAppViewModel.current.mainScreenViewModel.contactsPageViewModel
) {
    Text(text = "Contact Screen")
}