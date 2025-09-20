package com.fyyadi.jampy.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.fyyadi.domain.model.Plant
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans

@Composable
fun PlantCard(
    plant: Plant,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.clickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .width(180.dp)
                .padding(12.dp)
        ) {
            AsyncImage(
                model = plant.imagePlant,
                contentDescription = plant.plantName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = plant.plantName,
                fontFamily = RethinkSans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = plant.plantSpecies,
                fontSize = 12.sp,
                color = Color.Black,
                lineHeight = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = plant.plantDescription,
                fontSize = 10.sp,
                fontFamily = RethinkSans,
                color = PrimaryGreen,
                maxLines = 2,
                lineHeight = 14.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
