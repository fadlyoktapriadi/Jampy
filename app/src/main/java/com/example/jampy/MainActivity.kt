    package com.example.jampy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.jampy.ui.theme.JampyTheme
import kotlinx.coroutines.delay

    class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JampyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    var showSplashScreen: Boolean by remember { mutableStateOf(true) }

                    LaunchedEffect(Unit) {
                        delay(3000)
                        showSplashScreen = false
                    }

                    if (showSplashScreen) {
                        SplashScreen()
                    } else {
                        WelcomePage()
                    }
                }
            }
        }
    }
}