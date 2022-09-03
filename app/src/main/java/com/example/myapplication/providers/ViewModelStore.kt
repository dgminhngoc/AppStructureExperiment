package com.example.myapplication.providers

import com.example.myapplication.data.ServerDataRepository
import com.example.myapplication.data.IServerDataRepository
import com.example.myapplication.data.IUserDataRepository
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.viewmodel.*

interface IViewModelStore {
    fun <T: IViewModel> getViewModel(key: String): T
    fun clear()
}

class ViewModelStore: IViewModelStore {
    private val viewModels = mutableMapOf<String, IViewModel>()

    @Suppress("UNCHECKED_CAST")
    override fun <T: IViewModel> getViewModel(key: String): T{

        var viewModel = viewModels[key]
        if(viewModel == null) {
            viewModel = when(key) {
                AppViewModel::class.java.name -> {
                    AppViewModelImpl(getUserReferencesRepository())
                }
                LoginScreenViewModel::class.java.name -> {
                    LoginScreenViewModelImpl()
                }
                MainScreenViewModel::class.java.name -> {
                    MainScreenViewModelImpl()
                }
                LoginPageViewModel::class.java.name -> {
                    LoginPageViewModelImpl(dataRepository = getDataRepository())
                }
                RegistrationPageViewModel::class.java.name -> {
                    RegistrationPageViewModelImpl(getDataRepository())
                }
                ResetPasswordPageViewModel::class.java.name -> {
                    ResetPasswordPageViewModelImpl(getDataRepository())
                }
                HomePageViewModel::class.java.name -> {
                    HomePageViewModelImpl()
                }
                VideosPageViewModel::class.java.name -> {
                    VideosPageViewModelImpl()
                }
                ProductsPageViewModel::class.java.name -> {
                    ProductsPageViewModelImpl()
                }
                ContactsPageViewModel::class.java.name -> {
                    ContactsPageViewModelImpl()
                }
                else -> {
                    object: BaseViewModel(){}
                }
            }

            viewModels[key] = viewModel
            if(viewModel is BaseViewModel) {
                viewModel.addDisposeAction { removeViewModel(key)}
            }
        }

        return viewModel as T
    }

    private fun getDataRepository(): IServerDataRepository {
        return ServerDataRepository()
    }

    private fun getUserReferencesRepository(): IUserDataRepository {
        return UserDataRepository()
    }

    private fun removeViewModel(key: String) {
        val viewModel: IViewModel? = viewModels[key]
        viewModel?.let {
            viewModels.remove(key)
        }
    }

    override fun clear() {
        viewModels.forEach {element->
            element.value.dispose()
        }

        viewModels.clear()
    }
}