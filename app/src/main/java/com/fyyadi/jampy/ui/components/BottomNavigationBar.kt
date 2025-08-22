package com.fyyadi.jampy.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fyyadi.jampy.ui.theme.OrangePrimary
import com.fyyadi.jampy.ui.theme.backgroundCardWhite
import com.fyyadi.jampy.ui.theme.PrimaryGreen
import com.fyyadi.jampy.utils.BottomNavItem

@Composable
fun BottomNavigationBar(
    isBottomBarVisible: Boolean,
    itemNavScreens: List<BottomNavItem>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit
) {

    AnimatedVisibility(
        visible = isBottomBarVisible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
    ) {

        NavigationBar {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundCardWhite)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    itemNavScreens.forEachIndexed { index, item ->
                        val isSelected = selectedIndex == index
                        val isScanButton = index == 2

                        IconButton(onClick = { onSelectedIndexChange(index) }) {
                            Box(
                                modifier = if (isScanButton) {
                                    Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(OrangePrimary)
                                } else {
                                    Modifier.size(28.dp)
                                },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = if (isSelected) item.selectedIcon else item.unselectedIcon
                                    ),
                                    contentDescription = item.title,
                                    modifier = Modifier.size(if (isScanButton) 28.dp else 24.dp),
                                    tint = when {
                                        isScanButton -> Color.White
                                        isSelected -> PrimaryGreen
                                        else -> OrangePrimary
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}