package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.RequestResult
import com.example.myapplication.providers.LocalViewModelProvider
import com.example.myapplication.providers.ViewModelsProvider
import com.example.myapplication.providers.ViewModels
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.*

@Preview(
    widthDp = 400,
    heightDp = 700
)
@Composable
fun ResetPasswordPagePreview() {
    val viewModels = ViewModelsProvider()
    MyApplicationTheme {
        CompositionLocalProvider(LocalViewModelProvider provides viewModels) {
            ResetPasswordPage()
        }
    }
}

@Composable
fun ResetPasswordPage(
    resetPasswordPageViewModel: IResetPasswordPageViewModel =
        ViewModels.get(IResetPasswordPageViewModel::class.java.name),
    loginScreenViewModel: ILoginScreenViewModel =
        ViewModels.get(ILoginScreenViewModel::class.java.name),
) {
    val loginSelectedPageIndexState by loginScreenViewModel.selectedPageIndexState.collectAsState()
    DisposableEffect(loginSelectedPageIndexState) {
        onDispose {
            if(loginSelectedPageIndexState != LoginNavigatePage.RESET_PASSWORD) {
                resetPasswordPageViewModel.dispose()
            }
        }
    }

    val resetPasswordPageUIState by resetPasswordPageViewModel.resetPasswordPageUIState.collectAsState()
    if(resetPasswordPageUIState is ResetPasswordPageUIState.ResultReceived
        && (resetPasswordPageUIState as ResetPasswordPageUIState.ResultReceived)
            .requestResult is RequestResult.OnSuccess) {
        ResetPasswordSuccessPage(
            loginScreenViewModel = loginScreenViewModel
        )
    }
    else {
        ResetPasswordFormPage(
            resetPasswordPageViewModel = resetPasswordPageViewModel,
            loginScreenViewModel = loginScreenViewModel
        )
    }
}

@Composable
fun ResetPasswordFormPage(
    resetPasswordPageViewModel: IResetPasswordPageViewModel,
    loginScreenViewModel: ILoginScreenViewModel
) {
    val resetPasswordFormState by resetPasswordPageViewModel.resetPasswordFormState.collectAsState()
    var email = resetPasswordFormState.email

    val resetPasswordPageUIState by resetPasswordPageViewModel.resetPasswordPageUIState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp,)
    ) {
        Text(
            modifier = Modifier.padding(top = 20.dp, bottom = 30.dp),
            text = "Forgot password",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            fontSize = 25.sp
        )

        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Please enter the email address you used to create your WP account. We will then email you a link that you can use to reset your password",
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                enabled = resetPasswordPageUIState !is ResetPasswordPageUIState.RequestSending,
                value = email,
                onValueChange = {
                    email = it
                    resetPasswordPageViewModel.onEvent(
                        ResetPasswordFormEvent.ResetPasswordFormChanged(
                            email = email,
                        ))
                },
                isError = resetPasswordFormState.emailError != null,
                placeholder = { Text("E-Mail") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        resetPasswordPageViewModel.onEvent(ResetPasswordFormEvent.ResetPasswordFormSubmit)
                    }
                ),
            )
            resetPasswordFormState.emailError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(Modifier.fillMaxHeight())
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = resetPasswordPageUIState !is ResetPasswordPageUIState.RequestSending,
            onClick = {
                resetPasswordPageViewModel.onEvent(ResetPasswordFormEvent.ResetPasswordFormSubmit)
            }
        ) {
            Text(text = if(resetPasswordPageUIState is ResetPasswordPageUIState.RequestSending) {
                "Sending request..."
            } else {
                "Reset Password"
            })
        }

        Text(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 20.dp)
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

@Composable
fun ResetPasswordSuccessPage(
    loginScreenViewModel: ILoginScreenViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Icon(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(bottom = 10.dp),
            imageVector = Icons.Outlined.Email,
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )

        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "YOUR PASSWORD IS ON THE WAY",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = MaterialTheme.colors.onSurface,
        )

        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Please open your email and click on the link to create a new password",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface,
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