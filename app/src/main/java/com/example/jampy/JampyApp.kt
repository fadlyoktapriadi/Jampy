package com.example.jampy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jampy.ui.theme.Outfit
import com.example.jampy.ui.theme.primaryGreen
import com.example.jampy.ui.theme.secondaryGreen
import com.example.jampy.ui.theme.tersiaryGreen

@Composable
fun JampyApp() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = secondaryGreen
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )
                )
        ){
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.jampy),
                        contentDescription = "Logo application the name is Jampy",
                        modifier = Modifier.size(50.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_setting),
                        contentDescription = "Setting button for the app",
                        modifier = Modifier.size(25.dp)
                    )
                }
                Text(
                    text = "Hi,\n" +
                            "Fadly Oktapriadi",
                    color = primaryGreen,
                    modifier = Modifier.padding(16.dp),
                    fontFamily = Outfit,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                )
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = tersiaryGreen
                    ),
                    modifier = Modifier
                        .size(width = 250.dp, height = 200.dp)
                        .graphicsLayer {
                            rotationZ = 10f
                        }
                ) {
                    // Card 2 content
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = primaryGreen
                    ),
                    modifier = Modifier
                        .size(width = 250.dp, height = 200.dp)
                        .graphicsLayer {
                            rotationZ = 0f
                        }
                ) {
                    // Card 1 content
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJampyApp() {
    JampyApp()
}