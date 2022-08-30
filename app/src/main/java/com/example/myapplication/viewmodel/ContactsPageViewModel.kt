package com.example.myapplication.viewmodel

interface IContactsPageViewModel: IViewModel {

}

class ContactsPageViewModel(
    onDisposeAction: (() -> Unit)? = null
): IContactsPageViewModel, BaseViewModel(onDisposeAction = onDisposeAction){

}