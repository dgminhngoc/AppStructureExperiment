package com.example.myapplication.providers

import com.example.myapplication.domain.ServerDataRepository
import com.example.myapplication.domain.IServerDataRepository
import com.example.myapplication.domain.IUserDataRepository
import com.example.myapplication.domain.UserDataRepository
import com.example.myapplication.viewmodel.*

interface IViewModelProvider {
    fun <T: IViewModel> getViewModel(vmClass: Class<T>): T
}

class ViewModelProvider: IViewModelProvider {
    private val viewModels = mutableMapOf<String, IViewModel>()

    @Suppress("UNCHECKED_CAST")
    override fun <T: IViewModel> getViewModel(vmClass: Class<T>): T{
        val className = vmClass.name

        var viewModel: IViewModel? = viewModels[className]
        if(viewModel == null) {
            viewModel = when(className) {
                IAppViewModel::class.java.name -> {
                    AppViewModel(getUserReferencesRepository())
                }
                ILoginScreenViewModel::class.java.name -> {
                    LoginScreenViewModel(
                        onDisposeAction = {
                            removeViewModel(ILoginScreenViewModel::class.java)
                        }
                    )
                }
                IMainScreenViewModel::class.java.name -> {
                    MainScreenViewModel(
                        onDisposeAction = {
                            removeViewModel(IMainScreenViewModel::class.java)
                        }
                    )
                }
                ILoginPageViewModel::class.java.name -> {
                    LoginPageViewModel(
                        dataRepository = getDataRepository(),
                        onDisposeAction = {
                            removeViewModel(ILoginPageViewModel::class.java)
                        }
                    )
                }
                IRegistrationPageViewModel::class.java.name -> {
                    RegistrationPageViewModel(
                        getDataRepository(),
                        onDisposeAction = {
                            removeViewModel(IRegistrationPageViewModel::class.java)
                        }
                    )
                }
                IResetPasswordPageViewModel::class.java.name -> {
                    ResetPasswordPageViewModel(
                        getDataRepository(),
                        onDisposeAction = {
                            removeViewModel(IResetPasswordPageViewModel::class.java)
                        }
                    )
                }
                IHomePageViewModel::class.java.name -> {
                    HomePageViewModel(
                        onDisposeAction = {
                            removeViewModel(IHomePageViewModel::class.java)
                        }
                    )
                }
                IVideosPageViewModel::class.java.name -> {
                    VideosPageViewModel(
                        onDisposeAction = {
                            removeViewModel(IVideosPageViewModel::class.java)
                        }
                    )
                }
                IProductsPageViewModel::class.java.name -> {
                    ProductsPageViewModel(
                        onDisposeAction = {
                            removeViewModel(IProductsPageViewModel::class.java)
                        }
                    )
                }
                IContactsPageViewModel::class.java.name -> {
                    ContactsPageViewModel(
                        onDisposeAction = {
                            removeViewModel(IContactsPageViewModel::class.java)
                        }
                    )
                }
                else -> {
                    object: BaseViewModel(){}
                }
            }

            viewModels[className] = viewModel
        }

        return viewModel as T
    }

    private fun getDataRepository(): IServerDataRepository {
        return ServerDataRepository()
    }

    private fun getUserReferencesRepository(): IUserDataRepository {
        return UserDataRepository()
    }

    private fun <T: IViewModel> removeViewModel(vmClass: Class<T>) {
        val className = vmClass.name
        val viewModel: IViewModel? = viewModels[className]
        viewModel?.let {
            viewModels.remove(className)
        }
    }
}