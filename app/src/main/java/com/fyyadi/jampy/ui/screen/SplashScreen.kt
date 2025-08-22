package com.fyyadi.jampy.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fyyadi.jampy.R
import com.fyyadi.jampy.ui.theme.BackgroundGreen
import com.fyyadi.jampy.ui.theme.TersiaryGreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onLoggedNavigate: () -> Unit = {},
    onLoginNavigate: () -> Unit = {},
    modifier: Modifier
) {
    var splashscreen by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000L)
        splashscreen = false
    }

    if (splashscreen) {
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
                contentDescription = null,
                modifier = modifier.size(150.dp)
            )
        }
    } else {
        onLoginNavigate()
    }
}