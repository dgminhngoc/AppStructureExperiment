package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IContactsPageViewModel
import localProvider

@Composable
fun ContactsPage(
    contactsPageViewModel: IContactsPageViewModel = localProvider.current.getViewModel(IContactsPageViewModel::class.java),
) {
    Text(text = "Contact Screen")
}