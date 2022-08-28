package com.example.myapplication.viewmodel

abstract class IViewModel {
    private var onClearedAction: (() -> Unit)? = null

    fun setOnClearedAction(onClearedAction: (() -> Unit)? = null) {
        this.onClearedAction = onClearedAction
    }

    open fun onCleared(){
        onClearedAction?.invoke()
    }
}