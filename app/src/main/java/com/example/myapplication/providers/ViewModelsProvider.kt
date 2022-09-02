package com.example.myapplication.providers

import com.example.myapplication.data.ServerDataRepository
import com.example.myapplication.data.IServerDataRepository
import com.example.myapplication.data.IUserDataRepository
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.viewmodel.*

interface IViewModelsProvider {
    fun <T: IViewModel> getViewModel(key: String): T
}

class ViewModelsProvider: IViewModelsProvider {
    private val viewModels = mutableMapOf<String, IViewModel>()

    @Suppress("UNCHECKED_CAST")
    override fun <T: IViewModel> getViewModel(key: String): T{

        var viewModel: IViewModel? = viewModels[key]
        if(viewModel == null) {
            viewModel = when(key) {
                IAppViewModel::class.java.name -> {
                    AppViewModel(getUserReferencesRepository())
                }
                ILoginScreenViewModel::class.java.name -> {
                    LoginScreenViewModel(
                        onDisposeAction = {
                            removeViewModel(ILoginScreenViewModel::class.java.name)
                        }
                    )
                }
                IMainScreenViewModel::class.java.name -> {
                    MainScreenViewModel(
                        onDisposeAction = {
                            removeViewModel(IMainScreenViewModel::class.java.name)
                        }
                    )
                }
                ILoginPageViewModel::class.java.name -> {
                    LoginPageViewModel(
                        dataRepository = getDataRepository(),
                        onDisposeAction = {
                            removeViewModel(ILoginPageViewModel::class.java.name)
                        }
                    )
                }
                IRegistrationPageViewModel::class.java.name -> {
                    RegistrationPageViewModel(
                        getDataRepository(),
                        onDisposeAction = {
                            removeViewModel(IRegistrationPageViewModel::class.java.name)
                        }
                    )
                }
                IResetPasswordPageViewModel::class.java.name -> {
                    ResetPasswordPageViewModel(
                        getDataRepository(),
                        onDisposeAction = {
                            removeViewModel(IResetPasswordPageViewModel::class.java.name)
                        }
                    )
                }
                IHomePageViewModel::class.java.name -> {
                    HomePageViewModel(
                        onDisposeAction = {
                            removeViewModel(IHomePageViewModel::class.java.name)
                        }
                    )
                }
                IVideosPageViewModel::class.java.name -> {
                    VideosPageViewModel(
                        onDisposeAction = {
                            removeViewModel(IVideosPageViewModel::class.java.name)
                        }
                    )
                }
                IProductsPageViewModel::class.java.name -> {
                    ProductsPageViewModel(
                        onDisposeAction = {
                            removeViewModel(IProductsPageViewModel::class.java.name)
                        }
                    )
                }
                IContactsPageViewModel::class.java.name -> {
                    ContactsPageViewModel(
                        onDisposeAction = {
                            removeViewModel(IContactsPageViewModel::class.java.name)
                        }
                    )
                }
                else -> {
                    object: BaseViewModel(){}
                }
            }

            viewModels[key] = viewModel
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
}