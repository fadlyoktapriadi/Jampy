package com.fyyadi.jampy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fyyadi.jampy.ui.JampyApp
import com.fyyadi.theme.JampyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JampyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    JampyApp(
                        activity = this
                    )
                }
            }
        }
    }
}