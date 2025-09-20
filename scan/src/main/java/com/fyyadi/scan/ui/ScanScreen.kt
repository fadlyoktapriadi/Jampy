package com.fyyadi.scan.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fyyadi.scan.R
import com.fyyadi.theme.BackgroundGreen

@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGreen)

    ) {
        Image(
            painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.jampy),
            contentDescription = "Jampy Logo",
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp)
                .size(48.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(top = 86.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_placeholder_filled),
                        contentDescription = "Jampy Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(120.dp)
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Jampy Logo",
                    modifier = Modifier
                        .padding(start = 24.dp, top = 24.dp)
                        .size(48.dp)
                )
                Text(
                    text = "Take by Camera",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 24.dp, top = 24.dp)
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row {

                Image(
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = "Jampy Logo",
                    modifier = Modifier
                        .padding(start = 24.dp, top = 24.dp)
                        .size(48.dp)
                )
                Text(
                    text = "Choose from Gallery",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 24.dp, top = 24.dp)
                )
            }
        }










    }


}
