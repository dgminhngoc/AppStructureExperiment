package com.example.myapplication.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

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

abstract class MainScreenViewModel: BaseViewModel() {
    abstract val selectedTabIndexState: StateFlow<BottomNavTab>
    abstract fun onEvent(event: MainScreenEvent)
}

class MainScreenViewModelImpl: MainScreenViewModel() {
    private val _selectedTabIndexState = MutableStateFlow(BottomNavTab.HOME)
    override val selectedTabIndexState= _selectedTabIndexState.map { it }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.Eagerly,
        _selectedTabIndexState.value
    )

    override fun onEvent(event: MainScreenEvent) {
        when(event) {
            is MainScreenEvent.MainBottomTabNavigate -> {
                _selectedTabIndexState.value = event.tab
            }
        }
    }
}