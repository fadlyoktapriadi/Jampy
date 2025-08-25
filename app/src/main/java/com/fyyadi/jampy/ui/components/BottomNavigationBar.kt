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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fyyadi.jampy.ui.theme.Green600
import com.fyyadi.jampy.ui.theme.OrangePrimary
import com.fyyadi.jampy.ui.theme.backgroundCardWhite
import com.fyyadi.jampy.ui.theme.PrimaryGreen
import com.fyyadi.jampy.ui.theme.SlatePrimary
import com.fyyadi.jampy.utils.BottomNavItem
// file: `app/src/main/java/com/fyyadi/jampy/ui/components/BottomNavigationBar.kt`

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
        NavigationBar(
            containerColor = Green600,
            modifier = Modifier
                .fillMaxWidth()
                .background(Green600)
                .padding(bottom = 12.dp, top = 4.dp)
        ) {
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
                        BottomNavItemButton(
                            item = item,
                            isSelected = selectedIndex == index,
                            isScanButton = index == 2,
                            onClick = { onSelectedIndexChange(index) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavItemButton(
    item: BottomNavItem,
    isSelected: Boolean,
    isScanButton: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Box(
            modifier = Modifier.size(IconBoxSize),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = if (isSelected) item.selectedIcon else item.unselectedIcon
                ),
                contentDescription = item.title,
                modifier = Modifier.size(iconSize(isScanButton)),
                tint = iconTint(isScanButton, isSelected)
            )
        }
    }
}

private val IconBoxSize = 28.dp

private fun iconSize(isScanButton: Boolean) =
    if (isScanButton) 28.dp else 24.dp

private fun iconTint(isScanButton: Boolean, isSelected: Boolean) = when {
    isScanButton -> OrangePrimary
    isSelected   -> PrimaryGreen
    else         -> SlatePrimary
}