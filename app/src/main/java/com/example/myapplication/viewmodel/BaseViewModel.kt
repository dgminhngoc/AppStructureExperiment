package com.example.myapplication.viewmodel

interface IViewModel {
    fun dispose()
}

abstract class BaseViewModel: IViewModel {

    private val disposeActions = mutableListOf<(() -> Unit)>()

    fun addDisposeAction(action: () -> Unit) {
        disposeActions.add(action)
    }

    override fun dispose(){
        disposeActions.forEach { action->
            action.invoke()
        }

        disposeActions.clear()
    }
}