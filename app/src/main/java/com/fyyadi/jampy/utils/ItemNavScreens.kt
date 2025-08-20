package com.fyyadi.jampy.utils

import com.fyyadi.jampy.R
import com.fyyadi.jampy.route.HomeRoutes

fun getItemNavScreens(): List<BottomNavItem> =
    listOf(
        BottomNavItem(
            title = "Home",
            selectedIcon = R.drawable.home_filled,
            unselectedIcon = R.drawable.home_gray,
            route = HomeRoutes.HomeScreen
        ),
        BottomNavItem(
            title = "Search",
            selectedIcon = R.drawable.search_filled,
            unselectedIcon = R.drawable.search_grey,
            route = HomeRoutes.SearchScreen
        ),
        BottomNavItem(
            title = "Scan",
            selectedIcon = R.drawable.scan_orange,
            unselectedIcon = R.drawable.scan_grey,
            route = HomeRoutes.ScanScreen
        ),
        BottomNavItem(
            title = "Bookmark",
            selectedIcon = R.drawable.bookmark_filled,
            unselectedIcon = R.drawable.bookmark_grey,
            route = HomeRoutes.BookmarkScreen
        ),
        BottomNavItem(
            title = "Profile",
            selectedIcon = R.drawable.profile_filled,
            unselectedIcon = R.drawable.profile_grey,
            route = HomeRoutes.ProfileScreen
        )
    )