package com.example.myapplication.providers

import com.example.myapplication.domain.ServerDataRepository
import com.example.myapplication.domain.IServerDataRepository
import com.example.myapplication.domain.IUserDataRepository
import com.example.myapplication.domain.UserDataRepository
import com.example.myapplication.viewmodel.*

interface IViewModelProvider {
    fun <T: IViewModel> getViewModel(vmClass: Class<T>): T
    fun getDataRepository(): IServerDataRepository
    fun getUserReferencesRepository(): IUserDataRepository
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
                    LoginScreenViewModel().also {
                        it.setOnClearedAction {
                            removeViewModel(ILoginScreenViewModel::class.java)
                        }
                    }
                }
                IMainScreenViewModel::class.java.name -> {
                    MainScreenViewModel().also {
                        it.setOnClearedAction {
                            removeViewModel(IMainScreenViewModel::class.java)
                        }
                    }
                }
                ILoginPageViewModel::class.java.name -> {
                    LoginPageViewModel(getDataRepository()).also {
                        it.setOnClearedAction {
                            removeViewModel(ILoginPageViewModel::class.java)
                        }
                    }
                }
                IRegistrationPageViewModel::class.java.name -> {
                    RegistrationPageViewModel(getDataRepository()).also {
                        it.setOnClearedAction {
                            removeViewModel(IRegistrationPageViewModel::class.java)
                        }
                    }
                }
                IResetPasswordPageViewModel::class.java.name -> {
                    ResetPasswordPageViewModel(getDataRepository()).also {
                        it.setOnClearedAction {
                            removeViewModel(IResetPasswordPageViewModel::class.java)
                        }
                    }
                }
                IHomePageViewModel::class.java.name -> {
                    HomePageViewModel().also {
                        it.setOnClearedAction {
                            removeViewModel(IHomePageViewModel::class.java)
                        }
                    }
                }
                IVideosPageViewModel::class.java.name -> {
                    VideosPageViewModel().also {
                        it.setOnClearedAction {
                            removeViewModel(IVideosPageViewModel::class.java)
                        }
                    }
                }
                IProductsPageViewModel::class.java.name -> {
                    ProductsPageViewModel().also {
                        it.setOnClearedAction {
                            removeViewModel(IProductsPageViewModel::class.java)
                        }
                    }
                }
                IContactsPageViewModel::class.java.name -> {
                    ContactsPageViewModel().also {
                        it.setOnClearedAction {
                            removeViewModel(IContactsPageViewModel::class.java)
                        }
                    }
                }
                else -> {
                    object: IViewModel(){}
                }
            }

            viewModels[className] = viewModel
        }

        return viewModel as T
    }

    override fun getDataRepository(): IServerDataRepository {
        return ServerDataRepository()
    }

    override fun getUserReferencesRepository(): IUserDataRepository {
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