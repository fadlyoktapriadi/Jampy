package com.fyyadi.jampy.ui.components

import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.fyyadi.jampy.ui.theme.BackgroundGreen
import com.fyyadi.jampy.ui.theme.OrangePrimary
import com.fyyadi.jampy.ui.theme.backgroundCardWhite
import com.fyyadi.jampy.ui.theme.PrimaryGreen
import com.fyyadi.jampy.ui.theme.SlatePrimary
import com.fyyadi.jampy.utils.BottomNavItem
@Composable
fun BottomNavigationBar(
    navController: NavController,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(if(selectedIndex == 0) Color.Transparent else BackgroundGreen)
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(50)) // Menambah bayangan
                    .background(color = backgroundCardWhite, shape = RoundedCornerShape(50))
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemNavScreens.forEachIndexed { index, item ->
                    BottomNavItemButton(
                        isSelected = selectedIndex == index,
                        isScanButton = index == 2,
                        onClick = {
                            if (selectedIndex != index) {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                }
                                Log.d("BottomNav", "Navigating to ${item.route}, index $index")
                                onSelectedIndexChange(index)
                            }
                        },
                        item = item
                    )
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

// PERUBAHAN: Ukuran ikon scan dibuat lebih besar agar menonjol
private fun iconSize(isScanButton: Boolean) =
    if (isScanButton) 32.dp else 24.dp

// Logika pewarnaan ikon sudah sesuai dengan desain baru
private fun iconTint(isScanButton: Boolean, isSelected: Boolean) = when {
    isScanButton -> OrangePrimary // Tombol scan selalu oranye
    isSelected -> PrimaryGreen    // Item terpilih berwarna hijau
    else -> SlatePrimary          // Item tidak terpilih berwarna abu-abu
}