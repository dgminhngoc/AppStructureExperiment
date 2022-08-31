package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.RequestResult
import com.example.myapplication.providers.LocalViewModelProvider
import com.example.myapplication.providers.ViewModelProvider
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.*

@Preview(
    widthDp = 400,
    heightDp = 700
)
@Composable
fun RegisterPagePreview() {
    val viewModels = ViewModelProvider()
    MyApplicationTheme {
        CompositionLocalProvider(LocalViewModelProvider provides viewModels) {
            RegisterSuccessPage(LoginScreenViewModel())
        }
    }
}

@Composable
fun RegisterPage(
    registrationPageViewModel: IRegistrationPageViewModel =
        ViewModels.get(IRegistrationPageViewModel::class.java.name),
    loginScreenViewModel: ILoginScreenViewModel =
        ViewModels.get(ILoginScreenViewModel::class.java.name)
) {
    val loginSelectedPageIndexState by loginScreenViewModel.selectedPageIndexState.collectAsState()
    DisposableEffect(loginSelectedPageIndexState) {
        onDispose {
            if(loginSelectedPageIndexState != LoginNavigatePage.REGISTER) {
                registrationPageViewModel.dispose()
            }
        }
    }

    val registrationPageUIState by registrationPageViewModel.registrationPageUIState.collectAsState()
    if(registrationPageUIState is RegistrationPageUIState.ResultReceived
        && (registrationPageUIState as RegistrationPageUIState.ResultReceived)
            .requestResult is RequestResult.OnSuccess) {
        RegisterSuccessPage(
            loginScreenViewModel = loginScreenViewModel
        )
    }
    else {
        RegisterFormPage(
            registrationPageViewModel = registrationPageViewModel,
            loginScreenViewModel = loginScreenViewModel
        )
    }
}

@Composable
fun RegisterFormPage(
    registrationPageViewModel: IRegistrationPageViewModel,
    loginScreenViewModel: ILoginScreenViewModel
) {
    val registrationPageUIState by registrationPageViewModel.registrationPageUIState.collectAsState()

    val registrationFormState by registrationPageViewModel.registrationFormState.collectAsState()
    var email = registrationFormState.email
    var password = registrationFormState.password
    var repeatedPassword = registrationFormState.repeatedPassword
    var firstName = registrationFormState.firstName
    var lastName = registrationFormState.lastname
    var isTermsAccepted = registrationFormState.isTermsAccepted
    val isPasswordVisible = registrationFormState.isPasswordVisible
    val isRepeatedPasswordVisible = registrationFormState.isRepeatedPasswordVisible

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(start = 30.dp, end = 30.dp,)
    ) {
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = "Register",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            fontSize = 25.sp
        )

        Box {
            Column {
                val focusManager = LocalFocusManager.current
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = registrationPageUIState !is RegistrationPageUIState.RequestSending,
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
                            isPasswordVisible = isPasswordVisible,
                            isRepeatedPasswordVisible = isRepeatedPasswordVisible,
                        ))
                    },
                    isError = registrationFormState.emailError != null,
                    placeholder = { Text("E-Mail") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                )
                registrationFormState.emailError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    enabled = registrationPageUIState !is RegistrationPageUIState.RequestSending,
                    value = password,
                    visualTransformation =
                    if (isPasswordVisible) {
                        VisualTransformation.None
                    }
                    else {
                        PasswordVisualTransformation()
                    },
                    onValueChange = {
                        password = it
                        registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                            email = email,
                            password = password,
                            repeatedPassword = repeatedPassword,
                            firstName = firstName,
                            lastName = lastName,
                            isTermsAccepted = isTermsAccepted,
                            isPasswordVisible = isPasswordVisible,
                            isRepeatedPasswordVisible = isRepeatedPasswordVisible,
                        ))
                    },
                    isError = registrationFormState.passwordError != null,
                    placeholder = { Text("Password") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    trailingIcon = {
                        val image = if (isPasswordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        // Please provide localized description for accessibility services
                        val description = if (isPasswordVisible) "Hide password" else "Show password"

                        IconButton(
                            onClick = {
                                registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                                    email = email,
                                    password = password,
                                    repeatedPassword = repeatedPassword,
                                    firstName = firstName,
                                    lastName = lastName,
                                    isTermsAccepted = isTermsAccepted,
                                    isPasswordVisible = !isPasswordVisible,
                                    isRepeatedPasswordVisible = isRepeatedPasswordVisible,
                                ))
                            }
                        ){
                            Icon(imageVector  = image, description)
                        }
                    },
                )
                registrationFormState.passwordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    enabled = registrationPageUIState !is RegistrationPageUIState.RequestSending,
                    visualTransformation =
                        if (isRepeatedPasswordVisible) {
                            VisualTransformation.None
                        }
                        else {
                            PasswordVisualTransformation()
                        },
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
                            isPasswordVisible = isPasswordVisible,
                            isRepeatedPasswordVisible = isRepeatedPasswordVisible,
                        ))
                    },
                    isError = registrationFormState.repeatedPasswordError != null,
                    placeholder = { Text("Repeat password") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    trailingIcon = {
                        val image = if (isRepeatedPasswordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        // Please provide localized description for accessibility services
                        val description = if (isRepeatedPasswordVisible) "Hide password" else "Show password"

                        IconButton(
                            onClick = {
                                registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                                    email = email,
                                    password = password,
                                    repeatedPassword = repeatedPassword,
                                    firstName = firstName,
                                    lastName = lastName,
                                    isTermsAccepted = isTermsAccepted,
                                    isPasswordVisible = isPasswordVisible,
                                    isRepeatedPasswordVisible = !isRepeatedPasswordVisible,
                                ))
                            }
                        ){
                            Icon(imageVector  = image, description)
                        }
                    },
                )
                registrationFormState.repeatedPasswordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    enabled = registrationPageUIState !is RegistrationPageUIState.RequestSending,
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
                            isPasswordVisible = isPasswordVisible,
                            isRepeatedPasswordVisible = isRepeatedPasswordVisible,
                        ))
                    },
                    isError = registrationFormState.firstNameError != null,
                    placeholder = { Text("First Name") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                )
                registrationFormState.firstNameError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    enabled = registrationPageUIState !is RegistrationPageUIState.RequestSending,
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
                            isPasswordVisible = isPasswordVisible,
                            isRepeatedPasswordVisible = isRepeatedPasswordVisible,
                        ))
                    },
                    isError = registrationFormState.lastnameError != null,
                    placeholder = { Text("Last Name") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(force = true)
                        }
                    ),
                )
                registrationFormState.lastnameError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Checkbox(
                        checked = isTermsAccepted,
                        colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
                        enabled = registrationPageUIState !is RegistrationPageUIState.RequestSending,
                        onCheckedChange = {
                            isTermsAccepted = it
                            registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormChanged(
                                email = email,
                                password = password,
                                repeatedPassword = repeatedPassword,
                                firstName = firstName,
                                lastName = lastName,
                                isTermsAccepted = isTermsAccepted,
                                isPasswordVisible = isPasswordVisible,
                                isRepeatedPasswordVisible = isRepeatedPasswordVisible,
                            ))
                        },
                    )
                    Text(text = "Terms Accept", modifier = Modifier.padding(start = 0.dp))
                }
                registrationFormState.isTermsAcceptedError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
        }

        Box(modifier = Modifier.padding(bottom = 20.dp)) {
            Column {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = registrationPageUIState !is RegistrationPageUIState.RequestSending,
                    onClick = {
                        registrationPageViewModel.onEvent(RegistrationFormEvent.RegistrationFormSubmit)
                    }
                ) {
                    Text(text = if(registrationPageUIState is RegistrationPageUIState.RequestSending) {
                        "Registering..."
                    } else {
                        "Register"
                    })
                }

                Text(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clickable {
                            loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.LOGIN))
                        }
                        .align(Alignment.CenterHorizontally),
                    text = "Cancel",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun RegisterSuccessPage(
    loginScreenViewModel: ILoginScreenViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )   {
        Icon(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(bottom = 10.dp),
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "ONE MORE STEP",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Please open your email and click on the link to confirm your accout",
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier
                .clickable {
                    loginScreenViewModel.onEvent(LoginScreenEvent.LoginPageNavigate(page = LoginNavigatePage.LOGIN))
                },
            text = "Back to login",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
    }
}