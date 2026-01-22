package com.fyyadi.jampy.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
private fun rememberShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )
    val base = Color(0xFFE0E0E0)
    val highlight = Color(0xFFF5F5F5)
    return Brush.linearGradient(
        colors = listOf(base.copy(alpha = 0.6f), highlight, base.copy(alpha = 0.6f)),
        start = Offset.Zero,
        end = Offset(x = translate, y = translate)
    )
}

@Composable
fun ShimmerPlantCard(modifier: Modifier = Modifier, search: Boolean = false, ) {
    val brush = rememberShimmerBrush()
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = if (search) modifier.fillMaxWidth()
        else modifier.width(180.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(brush)
            )
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .height(14.dp)
                    .fillMaxWidth(0.7f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth(0.85f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
        }
    }
}