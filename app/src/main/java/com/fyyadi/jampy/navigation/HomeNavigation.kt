package com.fyyadi.jampy.navigation

import android.app.Activity
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fyyadi.jampy.route.AuthRoutes
import com.fyyadi.jampy.route.HomeRoutes
import com.fyyadi.jampy.route.ManagementRoutes
import com.fyyadi.jampy.ui.screen.activity.ActivityHistoryScreen
import com.fyyadi.jampy.ui.screen.activity.DetailActivityHistoryScanScreen
import com.fyyadi.jampy.ui.screen.bookmark.BookmarkScreen
import com.fyyadi.jampy.ui.screen.detail.DetailPlantScreen
import com.fyyadi.jampy.ui.screen.home.HomeScreen
import com.fyyadi.jampy.ui.screen.profile.ProfileScreen

object HomeNavigation {

    fun NavGraphBuilder.homeNavigation(
        navController: NavController,
        activity: Activity,
        modifier: Modifier
    ) {
        composable<HomeRoutes.HomeScreen> {
            HomeScreen(
                modifier = modifier,
                onBackPressed = {
                    activity.finish()
                },
                onPlantClick = { idPlant ->
                    navController.navigate(HomeRoutes.DetailPlantScreen(idPlant))
                }
            )
        }

        composable<HomeRoutes.DetailPlantScreen> { backStackEntry ->
            val plant = backStackEntry.toRoute<HomeRoutes.DetailPlantScreen>()
            DetailPlantScreen(
                idPlant = plant.plantId,
                onBackClick = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable<HomeRoutes.ActivityHistoryScan> {
            ActivityHistoryScreen(
                modifier = modifier,
                onBackPressed = {
                    navController.navigate(HomeRoutes.HomeScreen) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onPlantClick = { plantId, imageResultUri, accuracy ->
                    navController.navigate(
                        HomeRoutes.DetailActivityHistoryScanScreen(
                            plantId = plantId,
                            imageResultUri = imageResultUri,
                            accuracy = accuracy
                        )
                    )
                }
            )
        }

        composable<HomeRoutes.BookmarkScreen> {
            BookmarkScreen(
                modifier = modifier,
                onBackPressed = {
                    navController.navigate(HomeRoutes.HomeScreen) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onPlantClick = { idPlant ->
                    navController.navigate(HomeRoutes.DetailPlantScreen(idPlant))
                },
            )
        }

        composable<HomeRoutes.ProfileScreen> {
            ProfileScreen(
                modifier = modifier,
                onBackPressed = {
                    navController.navigate(HomeRoutes.HomeScreen) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onLoggedOut = {
                    navController.navigate(AuthRoutes.LoginScreen)
                },
                onPlantManagementNavigate = {
                    navController.navigate(ManagementRoutes.PlantManagementScreen)
                },
                onUsersManagementNavigate = {
                    navController.navigate(ManagementRoutes.UsersManagementScreen)
                }
            )
        }

        composable<HomeRoutes.DetailActivityHistoryScanScreen> { backStackEntry ->
            val history = backStackEntry.toRoute<HomeRoutes.DetailActivityHistoryScanScreen>()

            DetailActivityHistoryScanScreen(
                idPlant = history.plantId,
                imageResultUri = history.imageResultUri,
                accuracy = history.accuracy,
                onBackClick = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

    }
}