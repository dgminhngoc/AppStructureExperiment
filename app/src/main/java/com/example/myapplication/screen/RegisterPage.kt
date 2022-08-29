package com.example.myapplication.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.viewmodel.*

@Composable
fun RegisterPage(
    registrationPageViewModel: IRegistrationPageViewModel =
        ViewModels.get(IRegistrationPageViewModel::class.java),
    loginScreenViewModel: ILoginScreenViewModel =
        ViewModels.get(ILoginScreenViewModel::class.java)
) {
    val loginSelectedPageIndexState by loginScreenViewModel.selectedPageIndexState.collectAsState()
    DisposableEffect(loginSelectedPageIndexState) {
        onDispose {
            if(loginSelectedPageIndexState != LoginNavigatePage.REGISTER) {
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
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                )
        ) {

            OutlinedTextField(
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
            registrationPageViewModel.registrationFormState.value.emailError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = password,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
            registrationPageViewModel.registrationFormState.value.passwordError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
            registrationPageViewModel.registrationFormState.value.repeatedPasswordError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
            OutlinedTextField(
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
            registrationPageViewModel.registrationFormState.value.firstNameError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
            OutlinedTextField(
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
            registrationPageViewModel.registrationFormState.value.lastnameError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }

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
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
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
            Image(
                painter = painterResource(id = android.R.drawable.ic_dialog_email),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
                contentDescription = ""
            )
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