package com.example.myapplication.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

enum class BottomNavTab(val rank: Int) {
    HOME(0),
    VIDEOS(1),
    PRODUCTS(2),
    CONTACTS(3),
}

class MainScreenViewModel: IViewModel() {
    val selectedTabIndexState: MutableState<BottomNavTab> = mutableStateOf(BottomNavTab.HOME)

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

    fun selectTab(bottomNavTab: BottomNavTab) {
        disposeTabViewModel(selectedTabIndexState.value)

        selectedTabIndexState.value = bottomNavTab
    }

    override fun dispose() {
        disposeTabViewModel(BottomNavTab.HOME)
        disposeTabViewModel(BottomNavTab.VIDEOS)
        disposeTabViewModel(BottomNavTab.PRODUCTS)
        disposeTabViewModel(BottomNavTab.CONTACTS)
    }

    private fun disposeTabViewModel(bottomNavTab: BottomNavTab) {
        when(bottomNavTab) {
            BottomNavTab.HOME -> {
                _homePageViewModel?.dispose()
                _homePageViewModel = null
            }
            BottomNavTab.VIDEOS -> {
                _videosPageViewModel?.dispose()
                _videosPageViewModel = null
            }
            BottomNavTab.PRODUCTS -> {
                _productsPageViewModel?.dispose()
                _productsPageViewModel = null
            }
            BottomNavTab.CONTACTS -> {
                _contactsPageViewModel?.dispose()
                _contactsPageViewModel = null
            }
        }
    }
}