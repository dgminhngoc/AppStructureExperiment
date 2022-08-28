package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.IContactsPageViewModel
import com.example.myapplication.viewmodel.localViewModelProvider

@Composable
fun ContactsPage(
    contactsPageViewModel: IContactsPageViewModel = localViewModelProvider.current.getViewModel(IContactsPageViewModel::class.java),
) {
    Text(text = "Contact Screen")
}