package com.fyyadi.jampy.ui.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.theme.OrangePrimary
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans
import com.fyyadi.theme.backgroundCardWhite


@Composable
fun WeatherCard(
    profileUserState: ResultState<UserProfile?>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundCardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Location",
                        tint = OrangePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Pandeglang", fontSize = 12.sp, color = Color.Black)
                }
                Text(
                    text = "28°C",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangePrimary,
                    lineHeight = 50.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Hai,", fontSize = 16.sp, color = Color.Black)
                when(profileUserState)
                {
                    is ResultState.Success -> {
                        Text(
                            text = profileUserState.data?.fullName ?: "",
                            fontSize = 18.sp,
                            fontFamily = RethinkSans,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )
                    }
                    else -> {
                        Text(
                            text = "User",
                            fontSize = 18.sp,
                            fontFamily = RethinkSans,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )
                    }
                }
            }
        }
    }
}