package com.fyyadi.jampy.utils

import androidx.annotation.DrawableRes
import com.fyyadi.jampy.route.BaseRoute

data class BottomNavItem(
    val title: String,
    @param:DrawableRes val selectedIcon: Int,
    @param:DrawableRes val unselectedIcon: Int,
    val route: BaseRoute
)