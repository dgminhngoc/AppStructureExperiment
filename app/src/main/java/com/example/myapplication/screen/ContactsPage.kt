package com.example.myapplication.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.viewmodel.ContactsPageViewModel

@Composable
fun ContactsPage(
    contactsPageViewModel: ContactsPageViewModel
) {
    Text(text = "Contact Screen")
}