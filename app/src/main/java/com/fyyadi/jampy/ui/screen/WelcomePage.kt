package com.fyyadi.jampy.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fyyadi.jampy.R
import com.fyyadi.jampy.ui.theme.Amarant
import com.fyyadi.jampy.ui.theme.RethinkSans
import com.fyyadi.jampy.ui.theme.bgGreen
import com.fyyadi.jampy.ui.theme.primaryGreen

@Composable
fun WelcomePage() {
    Box(
        Modifier
            .fillMaxSize()
            .background(bgGreen),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.jampy),
                contentDescription = "Logo application the name is Jampy",
                modifier = Modifier.size(100.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(top = 14.dp)
            ) {
                Text(
                    text = "Nature's medicine,",
                    fontFamily = Amarant,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30.sp,
                    color = primaryGreen,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = "Explore the herbal world.",
                    fontFamily = Amarant,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = "Identifikasi tanaman dengan mudah dan temukan khasiatnya hanya dalam sekali klik.",
                    fontFamily = RethinkSans,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            Button(
                onClick = { /* TODO: Handle button click */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(12.dp, vertical = 4.dp)
                    .fillMaxWidth()
                    .border(1.dp, primaryGreen, shape = ButtonDefaults.shape)
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = "Google logo",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign in with Google",
                        fontSize = 14.sp,
                        fontFamily = RethinkSans,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomePage() {
    WelcomePage()
}
