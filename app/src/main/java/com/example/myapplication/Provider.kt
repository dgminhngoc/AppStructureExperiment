@file:Suppress("UNCHECKED_CAST")

package com.example.myapplication.viewmodel

import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.domain.DataRepository
import com.example.myapplication.domain.IDataRepository
import com.example.myapplication.domain.IUserPreferencesRepository
import com.example.myapplication.domain.UserPreferencesRepository

val localProvider = compositionLocalOf<Provider> { error("Provider not set") }

interface IProvider {
    fun <T: IViewModel> getViewModel(vmClass: Class<T>): T
    fun getDataRepository(): IDataRepository
    fun getUserReferencesRepository(): IUserPreferencesRepository
}

class Provider: IProvider {
    private val viewModels = mutableMapOf<String, IViewModel>()

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

    override fun getDataRepository(): IDataRepository {
        return DataRepository()
    }

    override fun getUserReferencesRepository(): IUserPreferencesRepository {
        return UserPreferencesRepository()
    }

    private fun <T: IViewModel> removeViewModel(vmClass: Class<T>) {
        val className = vmClass.name
        val viewModel: IViewModel? = viewModels[className]
        viewModel?.let {
            viewModels.remove(className)
        }
    }
}