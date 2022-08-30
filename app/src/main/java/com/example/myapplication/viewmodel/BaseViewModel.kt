package com.example.myapplication.viewmodel

interface IViewModel {
    fun dispose()
}

abstract class BaseViewModel(
    private val onDisposeAction: (() -> Unit)? = null
): IViewModel {

    override fun dispose(){
        onDisposeAction?.invoke()
    }
}