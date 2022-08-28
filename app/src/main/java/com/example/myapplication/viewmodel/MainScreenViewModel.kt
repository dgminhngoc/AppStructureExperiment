package com.example.myapplication.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

enum class BottomNavTab(val index: Int) {
    HOME(0),
    VIDEOS(1),
    PRODUCTS(2),
    CONTACTS(3),
}

sealed class MainScreenEvent {
    data class MainBottomTabNavigate(
        val tab: BottomNavTab
    ): MainScreenEvent()
}

abstract class IMainScreenViewModel: IViewModel() {
    abstract val selectedTabIndexState: MutableState<BottomNavTab>
    abstract fun onEvent(event: MainScreenEvent)
}

class MainScreenViewModel: IMainScreenViewModel() {
    override val selectedTabIndexState = mutableStateOf(BottomNavTab.HOME)

    override fun onEvent(event: MainScreenEvent) {
        when(event) {
            is MainScreenEvent.MainBottomTabNavigate -> {
                selectedTabIndexState.value = event.tab
            }
        }
    }
}