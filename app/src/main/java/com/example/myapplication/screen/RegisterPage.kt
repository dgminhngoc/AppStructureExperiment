package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.*
import localProvider

@Composable
fun RegisterPage(
    registrationPageViewModel: IRegistrationPageViewModel = localProvider.current.getViewModel(IRegistrationPageViewModel::class.java),
    loginScreenViewModel: ILoginScreenViewModel = localProvider.current.getViewModel(ILoginScreenViewModel::class.java)
) {
    DisposableEffect(loginScreenViewModel.selectedPageIndexState.value) {
        onDispose {
            if(loginScreenViewModel.selectedPageIndexState.value != LoginNavigatePage.REGISTER) {
                registrationPageViewModel.onCleared()
            }
        }
    }

    if(!registrationPageViewModel.registrationSubmittedState.value) {
        RegisterFormPage(
            registrationPageViewModel = registrationPageViewModel,
            loginScreenViewModel = loginScreenViewModel
        )
    }
    else{
        RegisterSuccessPage(
            loginScreenViewModel = loginScreenViewModel
        )
    }
}

@Composable
fun RegisterFormPage(
    registrationPageViewModel: IRegistrationPageViewModel,
    loginScreenViewModel: ILoginScreenViewModel
) {
    var email = registrationPageViewModel.registrationFormState.value.email
    var password = registrationPageViewModel.registrationFormState.value.password
    var repeatedPassword = registrationPageViewModel.registrationFormState.value.repeatedPassword
    var firstName = registrationPageViewModel.registrationFormState.value.firstName
    var lastName = registrationPageViewModel.registrationFormState.value.lastname
    var isTermsAccepted = registrationPageViewModel.registrationFormState.value.isTermsAccepted

    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                )
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = email,
                onValueChange = {
                    email = it
                    registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = registrationPageViewModel.registrationFormState.value.emailError != null,
                label = { Text("E-Mail") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                    registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = registrationPageViewModel.registrationFormState.value.passwordError != null,
                label = { Text("Password") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = repeatedPassword,
                onValueChange = {
                    repeatedPassword = it
                    registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = registrationPageViewModel.registrationFormState.value.repeatedPasswordError != null,
                label = { Text("Repeat password") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = firstName,
                onValueChange = {
                    firstName = it
                    registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = registrationPageViewModel.registrationFormState.value.firstNameError != null,
                label = { Text("First Name") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = lastName,
                onValueChange = {
                    lastName = it
                    registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = registrationPageViewModel.registrationFormState.value.lastnameError != null,
                label = { Text("Last Name") }
            )

            Row {
                Checkbox(
                    checked = isTermsAccepted,
                    modifier = Modifier.padding(16.dp),
                    onCheckedChange = {
                        isTermsAccepted = it
                        registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                            email = email,
                            password = password,
                            repeatedPassword = repeatedPassword,
                            firstName = firstName,
                            lastName = lastName,
                            isTermsAccepted = isTermsAccepted,
                        ))
                    },
                )
                Text(text = "Terms Accept", modifier = Modifier.padding(16.dp))
            }

            registrationPageViewModel.registrationFormState.value.isTermsAcceptedError?.let {
                Text(text = it)
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormSubmit)
                }
            ) {
                Text(text = "Register")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.LOGIN))
                }
            ) {
                Text(text = "Cancel")
            }
        }
    }
}

@Composable
fun RegisterSuccessPage(
    loginScreenViewModel: ILoginScreenViewModel
) {
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                )
        ) {
            Text(text = "Registration is successful")

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.LOGIN))
                }
            ) {
                Text(text = "Back to login screen")
            }
        }
    }
}