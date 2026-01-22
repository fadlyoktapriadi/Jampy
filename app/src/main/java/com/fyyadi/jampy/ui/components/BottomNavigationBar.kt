package com.fyyadi.jampy.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.fyyadi.theme.SlatePrimary
import com.fyyadi.jampy.utils.BottomNavItem
import com.fyyadi.theme.Amarant
import com.fyyadi.theme.Green600

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
    ) {
        NavigationBar(
            containerColor = Green600,
            modifier = Modifier
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        ) {
            itemNavScreens.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index
                NavigationBarItem(
                    selected = isSelected,
                    colors = NavigationBarItemColors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = SlatePrimary,
                        unselectedTextColor = SlatePrimary,
                        selectedIndicatorColor = Color.Unspecified,
                        disabledIconColor = Color.Unspecified,
                        disabledTextColor = Color.Unspecified
                    ),
                    modifier = Modifier.padding(vertical = 6.dp),
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                            onSelectedIndexChange(index)
                        }
                    },
                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (isSelected) item.selectedIcon else item.unselectedIcon
                            ),
                            modifier = Modifier.size(22.dp),
                            contentDescription = item.title,
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontFamily = Amarant,
                            fontSize = 13.sp,
                        )
                    }
                )
            }
        }
    }
}
