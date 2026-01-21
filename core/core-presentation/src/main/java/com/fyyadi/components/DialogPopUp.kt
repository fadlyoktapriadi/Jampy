package com.fyyadi.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fyyadi.theme.Green600
import com.fyyadi.theme.RethinkSans

@Composable
fun DialogPopUp(
    title: String,
    @DrawableRes imageRes: Int,
    description: String,
    onDismissRequest: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontFamily = RethinkSans,
                fontSize = 24.sp,
                color = Color.Black,
            )
            Spacer(Modifier.height(8.dp))
            Image(
                painter = painterResource(imageRes),
                contentDescription = title,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = description,
                fontFamily = RethinkSans,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Button(
//                    onClick = onCloseClick,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 8.dp, vertical = 4.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Green600
//                    )
//                ) {
//                    Text(
//                        text = "Tutup",
//                        color = Color.White,
//                        fontSize = 14.sp,
//                        fontFamily = RethinkSans,
//                        modifier = Modifier.padding(vertical = 4.dp)
//                    )
//                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = onCloseClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green600
                    )
                ) {
                    Text(
                        text = "Tutup",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = RethinkSans,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
