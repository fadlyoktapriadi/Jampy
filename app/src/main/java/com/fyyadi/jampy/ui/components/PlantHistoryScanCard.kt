package com.fyyadi.jampy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.fyyadi.scan.domain.model.HistoryScan
import com.fyyadi.theme.Green200
import com.fyyadi.theme.RethinkSans
import com.fyyadi.theme.SecondaryGreen
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PlantHistoryScanCard(
    history: HistoryScan,
    onPlantClick: (Int, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(
                onClick = {
                    onPlantClick(
                        history.plantId,
                        history.imageResultUri,
                        history.accuracy.toString()
                    )
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = history.imageResultUri,
                contentDescription = history.plantId.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = history.plantNamePredict,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "Akurasi: ${history.accuracy}%",
                    fontSize = 12.sp,
                    color = SecondaryGreen
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    @Suppress("DEPRECATION")
                    val formattedDate = remember(history.date) {
                        DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy, HH.mm", Locale("id", "ID"))
                            .format(
                                java.time.Instant.ofEpochMilli(history.date)
                                    .atZone(ZoneId.systemDefault())
                            )
                    }
                    Text(
                        text = formattedDate,
                        fontSize = 12.sp,
                        fontFamily = RethinkSans,
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .background(color = Green200, shape = RoundedCornerShape(12.dp))
                            .padding(vertical = 2.dp, horizontal = 8.dp)
                    )
                }
            }
        }
    }
}
