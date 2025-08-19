package com.fyyadi.jampy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.fyyadi.jampy.ui.screen.HomeScreen
import com.fyyadi.jampy.ui.screen.SplashScreen
import com.fyyadi.jampy.ui.screen.WelcomePage
import com.fyyadi.jampy.ui.theme.JampyTheme
//import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

//@AndroidEntryPoint
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
                        delay(1000)
                        showSplashScreen = false
                    }

                    if (showSplashScreen) {
                        SplashScreen()
                    } else {
//                        JampyApp(modifier = Modifier)
                    }
                }
            }
        }
    }
}