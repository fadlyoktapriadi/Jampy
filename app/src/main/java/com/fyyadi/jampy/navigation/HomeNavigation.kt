package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fyyadi.jampy.route.HomeRoutes
import com.fyyadi.jampy.ui.screen.home.HomeScreen

object HomeNavigation {

    fun NavGraphBuilder.homeNavigation(
        navController: NavController,
        modifier: Modifier
    ) {
        composable<HomeRoutes.HomeScreen> {
            HomeScreen(
                modifier = modifier
            )
        }
    }
}