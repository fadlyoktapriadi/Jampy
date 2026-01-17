package com.fyyadi.auth.presentation.ui.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fyyadi.common.ResultState
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.Green600
import com.fyyadi.theme.SecondaryGreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onLoggedNavigate: () -> Unit = {},
    onLoginNavigate: () -> Unit = {}
) {
    val viewModel: SplashViewModel = hiltViewModel()
    val loggedState by viewModel.loggedState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkUserLogin()
    }

    LaunchedEffect(loggedState) {
        if (loggedState is ResultState.Success || loggedState is ResultState.Error) {
            delay(3000L)

            when (loggedState) {
                is ResultState.Success -> {
                    val isLoggedIn = (loggedState as ResultState.Success<Boolean>).data
                    if (isLoggedIn) {
                        onLoggedNavigate()
                    } else {
                        onLoginNavigate()
                    }
                }
                is ResultState.Error -> {
                    onLoginNavigate()
                }
                else -> {
                    // No-op
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        BackgroundGreen,
                        Green600,
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.jampy),
            contentDescription = "App Logo",
            modifier = modifier.size(150.dp)
        )
    }
}