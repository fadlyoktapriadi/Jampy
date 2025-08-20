package com.fyyadi.jampy.utils

import androidx.annotation.DrawableRes
import com.fyyadi.jampy.route.BaseRoute

data class BottomNavItem(
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: BaseRoute
)