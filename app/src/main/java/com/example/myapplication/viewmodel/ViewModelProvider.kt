@file:Suppress("UNCHECKED_CAST")

package com.example.myapplication.viewmodel

import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.domain.IDataRepository

val localViewModelProvider = compositionLocalOf<ViewModelProvider> { error("ViewModelProvider not set") }

interface IViewModelProvider {
    fun <T: IViewModel> getViewModel(ofClass: Class<T>): T
}

class ViewModelProvider(
    private val dataRepository: IDataRepository
): IViewModelProvider {
    private val viewModels = mutableMapOf<String, IViewModel>()

    override fun <T: IViewModel> getViewModel(ofClass: Class<T>): T{
        val className = ofClass.name

        var viewModel: IViewModel? = viewModels[className]
        if(viewModel == null) {
            viewModel = when(className) {
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
                    LoginPageViewModel(dataRepository).also {
                        it.setOnClearedAction {
                            removeViewModel(ILoginPageViewModel::class.java)
                        }
                    }
                }
                IRegistrationPageViewModel::class.java.name -> {
                    RegistrationPageViewModel(dataRepository).also {
                        it.setOnClearedAction {
                            removeViewModel(IRegistrationPageViewModel::class.java)
                        }
                    }
                }
                IResetPasswordPageViewModel::class.java.name -> {
                    ResetPasswordPageViewModel(dataRepository).also {
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

    private fun <T: IViewModel> removeViewModel(ofClass: Class<T>) {
        val className = ofClass.name
        val viewModel: IViewModel? = viewModels[className]
        viewModel?.let {
            viewModels.remove(className)
        }
    }
}