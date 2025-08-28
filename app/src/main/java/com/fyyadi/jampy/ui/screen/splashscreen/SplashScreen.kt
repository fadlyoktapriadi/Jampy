package com.fyyadi.jampy.ui.screen.splashscreen

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
import com.fyyadi.jampy.R
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.jampy.ui.theme.BackgroundGreen
import com.fyyadi.jampy.ui.theme.TersiaryGreen
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
                else -> { /* Should not reach here */ }
            }
        }
    }

    // Always show splash UI while checking login status
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        BackgroundGreen,
                        TersiaryGreen,
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.jampy),
            contentDescription = "App Logo",
            modifier = modifier.size(150.dp)
        )
    }
}