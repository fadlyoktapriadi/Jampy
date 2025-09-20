package com.fyyadi.jampy.ui

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fyyadi.jampy.navigation.AuthNavigation.authNavigation
import com.fyyadi.jampy.navigation.HomeNavigation.homeNavigation
import com.fyyadi.jampy.route.AuthRoutes
import com.fyyadi.jampy.route.BaseRoute
import com.fyyadi.jampy.route.HomeRoutes
import com.fyyadi.jampy.ui.components.BottomNavigationBar
import com.fyyadi.jampy.utils.getItemNavScreens
import com.fyyadi.theme.Green600

@Composable
fun JampyApp(
    activity: Activity
) {
    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val screens = getItemNavScreens()
    var isBottomBarVisible by rememberSaveable {
        mutableStateOf(false)
    }

    navController.addOnDestinationChangedListener { _, navDestination, _ ->
        val destRoute = navDestination.route?.takeLastWhile { it != '.' }
        when (destRoute) {
            HomeRoutes.HomeScreen.toString() -> {
                selectedItemIndex = 0
            }

            HomeRoutes.SearchScreen.toString() -> {
                selectedItemIndex = 1
            }

            HomeRoutes.BookmarkScreen.toString() -> {
                selectedItemIndex = 2
            }

            HomeRoutes.ProfileScreen.toString() -> {
                selectedItemIndex = 3
            }

            else -> Unit
        }

        isBottomBarVisible = screens.any { screen ->
            screen.route.toString() == destRoute
        }
    }

    Scaffold(
        containerColor = Green600,
        modifier = Modifier
            .fillMaxWidth(),
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                isBottomBarVisible,
                itemNavScreens = screens,
                selectedIndex = selectedItemIndex,
                onSelectedIndexChange = { selectedItemIndex = it }
            )
        },
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = AuthRoutes.SplashScreen,
            route = BaseRoute.BaseGraph::class,
            modifier = Modifier.padding(innerPadding)
        ) {
            authNavigation(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )

            homeNavigation(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}