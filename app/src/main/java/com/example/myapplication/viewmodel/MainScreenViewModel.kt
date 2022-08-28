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

    private var _homePageViewModel: HomePageViewModel? = null
    val homePageViewModel: HomePageViewModel
        get() {
            _homePageViewModel = _homePageViewModel ?: HomePageViewModel()
            return _homePageViewModel!!
        }

    private var _videosPageViewModel: VideosPageViewModel? = null
    val videosPageViewModel: VideosPageViewModel
        get() {
            _videosPageViewModel = _videosPageViewModel ?: VideosPageViewModel()
            return _videosPageViewModel!!
        }

    private var _productsPageViewModel: ProductsPageViewModel? = null
    val productsPageViewModel: ProductsPageViewModel
        get() {
            _productsPageViewModel = _productsPageViewModel ?: ProductsPageViewModel()
            return _productsPageViewModel!!
        }

    private var _contactsPageViewModel: ContactsPageViewModel? = null
    val contactsPageViewModel: ContactsPageViewModel
        get() {
            _contactsPageViewModel = _contactsPageViewModel ?: ContactsPageViewModel()
            return _contactsPageViewModel!!
        }

    override fun onEvent(event: MainScreenEvent) {
        when(event) {
            is MainScreenEvent.MainBottomTabNavigate -> {
                disposeTabViewModel(selectedTabIndexState.value)
                selectedTabIndexState.value = event.tab
            }
        }
    }

    override fun onCleared() {
        disposeTabViewModel(BottomNavTab.HOME)
        disposeTabViewModel(BottomNavTab.VIDEOS)
        disposeTabViewModel(BottomNavTab.PRODUCTS)
        disposeTabViewModel(BottomNavTab.CONTACTS)
    }

    private fun disposeTabViewModel(bottomNavTab: BottomNavTab) {
        when(bottomNavTab) {
            BottomNavTab.HOME -> {
                _homePageViewModel?.onCleared()
                _homePageViewModel = null
            }
            BottomNavTab.VIDEOS -> {
                _videosPageViewModel?.onCleared()
                _videosPageViewModel = null
            }
            BottomNavTab.PRODUCTS -> {
                _productsPageViewModel?.onCleared()
                _productsPageViewModel = null
            }
            BottomNavTab.CONTACTS -> {
                _contactsPageViewModel?.onCleared()
                _contactsPageViewModel = null
            }
        }
    }
}