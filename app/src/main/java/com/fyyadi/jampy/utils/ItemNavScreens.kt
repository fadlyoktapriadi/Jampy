package com.fyyadi.jampy.utils

import com.fyyadi.core_presentation.R
import com.fyyadi.jampy.route.HomeRoutes
import com.fyyadi.jampy.route.ScanRoutes

fun getItemNavScreens(): List<BottomNavItem> =
    listOf(
        BottomNavItem(
            title = "Home",
            selectedIcon = R.drawable.ic_home,
            unselectedIcon = R.drawable.ic_home_unselect,
            route = HomeRoutes.HomeScreen
        ),
        BottomNavItem(
            title = "Activity",
            selectedIcon = R.drawable.ic_activity,
            unselectedIcon = R.drawable.ic_activity_unselect,
            route = HomeRoutes.ActivityHistoryScan
        ),
        BottomNavItem(
            title = "Scan",
            selectedIcon = R.drawable.ic_scan,
            unselectedIcon = R.drawable.ic_scan_unselect,
            route = ScanRoutes.ScanScreen
        ),
        BottomNavItem(
            title = "Bookmark",
            selectedIcon = R.drawable.ic_bookmark,
            unselectedIcon = R.drawable.ic_bookmark_unselect,
            route = HomeRoutes.BookmarkScreen
        ),
        BottomNavItem(
            title = "Profile",
            selectedIcon = R.drawable.ic_profile,
            unselectedIcon = R.drawable.ic_profile_unselect,
            route = HomeRoutes.ProfileScreen
        )
    )