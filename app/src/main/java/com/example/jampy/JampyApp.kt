package com.example.jampy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jampy.ui.screen.SplashScreen
import com.example.jampy.ui.screen.WelcomePage
import kotlinx.coroutines.delay


@Composable
fun JampyApp(modifier: Modifier) {
    var showSplashScreen: Boolean by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000) // Delay for 3 seconds
        showSplashScreen = false
    }

    if (showSplashScreen) {
        SplashScreen()
    } else {
        WelcomePage(modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJampyyApp() {
    JampyApp(Modifier)
}