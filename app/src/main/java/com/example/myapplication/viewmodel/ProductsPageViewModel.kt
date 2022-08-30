package com.example.myapplication.viewmodel

interface IProductsPageViewModel: IViewModel {

}

class ProductsPageViewModel(
    onDisposeAction: (() -> Unit)? = null
): IProductsPageViewModel, BaseViewModel(onDisposeAction = onDisposeAction) {

}