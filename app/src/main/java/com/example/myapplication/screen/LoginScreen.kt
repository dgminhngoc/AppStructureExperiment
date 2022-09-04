package com.example.myapplication.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.providers.getViewModel
import com.example.myapplication.viewmodel.*

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = getViewModel(),
    appViewModel: AppViewModel = getViewModel(),
) {
    val appSelectedScreenIndexState by appViewModel.selectedScreenIndexState.collectAsState()
    DisposableEffect(appSelectedScreenIndexState) {
        onDispose {
            if(appSelectedScreenIndexState != AppScreen.LOGIN) {
                loginScreenViewModel.dispose()
            }
        }
    }

    BoxWithConstraints {
        val constraintsScope = this
        Row {
            if(constraintsScope.maxHeight >= 450.dp
                && LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Box(modifier = Modifier.weight(1f)) {
                    LogoScreen()
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                when (loginScreenViewModel.selectedPageIndexState.collectAsState().value) {
                    LoginNavigatePage.LOGIN -> {
                        LoginPage()
                    }
                    LoginNavigatePage.REGISTER -> {
                        RegisterPage()
                    }
                    LoginNavigatePage.RESET_PASSWORD -> {
                        ResetPasswordPage()
                    }
                }
            }
        }
    }
}

@Composable
fun LogoScreen() {
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
                .height(150.dp)
                .width(150.dp)
                .padding(bottom = 20.dp),
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )

        Text(
            text = "My App",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            fontSize = 25.sp
        )
    }
}