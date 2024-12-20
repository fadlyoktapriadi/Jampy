package com.example.jampy.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jampy.R
import com.example.jampy.ui.theme.BgGrey

@Composable
fun SplashScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BgGrey),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.jampy),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }
}

@Preview(showBackground = true)
@Composable
fun PreviewJampyyApp() {
    SplashScreen()
}