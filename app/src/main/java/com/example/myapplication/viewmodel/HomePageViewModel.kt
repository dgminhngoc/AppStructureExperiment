package com.example.myapplication.viewmodel

interface IHomePageViewModel: IViewModel {

}

class HomePageViewModel(
    onDisposeAction: (() -> Unit)? = null
): IHomePageViewModel, BaseViewModel(onDisposeAction = onDisposeAction) {

}