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

@Composable
fun RegisterPage(
    registrationPageViewModel: RegistrationPageViewModel,
    loginScreenViewModel: LoginScreenViewModel
) {
    if(!registrationPageViewModel.registrationSubmittedState.value) {
        RegisterFormPage(
            viewModel = registrationPageViewModel,
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
    viewModel: RegistrationPageViewModel,
    loginScreenViewModel: LoginScreenViewModel
) {
    var email by remember { mutableStateOf(viewModel.registrationFormState.value.email) }
    var password by remember { mutableStateOf(viewModel.registrationFormState.value.password) }
    var repeatedPassword by remember { mutableStateOf(viewModel.registrationFormState.value.repeatedPassword) }
    var firstName by remember { mutableStateOf(viewModel.registrationFormState.value.firstName) }
    var lastName by remember { mutableStateOf(viewModel.registrationFormState.value.lastname) }
    var isTermsAccepted by remember { mutableStateOf(viewModel.registrationFormState.value.isTermsAccepted) }

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
                    viewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = viewModel.registrationFormState.value.emailError != null,
                label = { Text("E-Mail") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                    viewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = viewModel.registrationFormState.value.passwordError != null,
                label = { Text("Password") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = repeatedPassword,
                onValueChange = {
                    repeatedPassword = it
                    viewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = viewModel.registrationFormState.value.repeatedPasswordError != null,
                label = { Text("Repeat password") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = firstName,
                onValueChange = {
                    firstName = it
                    viewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = viewModel.registrationFormState.value.firstNameError != null,
                label = { Text("First Name") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = lastName,
                onValueChange = {
                    lastName = it
                    viewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                        email = email,
                        password = password,
                        repeatedPassword = repeatedPassword,
                        firstName = firstName,
                        lastName = lastName,
                        isTermsAccepted = isTermsAccepted,
                    ))
                },
                isError = viewModel.registrationFormState.value.lastnameError != null,
                label = { Text("Last Name") }
            )

            Row {
                Checkbox(
                    checked = isTermsAccepted,
                    modifier = Modifier.padding(16.dp),
                    onCheckedChange = {
                        isTermsAccepted = it
                        viewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
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

            viewModel.registrationFormState.value.isTermsAcceptedError?.let {
                Text(text = it)
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onEvent(RegistrationFormEvent.RegistrationFormSubmit)
                }
            ) {
                Text(text = "Register")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginPage.LOGIN))
                }
            ) {
                Text(text = "Cancel")
            }
        }
    }
}

@Composable
fun RegisterSuccessPage(
    loginScreenViewModel: LoginScreenViewModel
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
                    loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginPage.LOGIN))
                }
            ) {
                Text(text = "Back to login screen")
            }
        }
    }
}