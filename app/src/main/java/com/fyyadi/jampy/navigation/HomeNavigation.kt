package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fyyadi.jampy.route.HomeRoutes
import com.fyyadi.jampy.ui.screen.bookmark.BookmarkScreen
import com.fyyadi.jampy.ui.screen.detail.DetailPlantScreen
import com.fyyadi.jampy.ui.screen.home.HomeScreen
import com.fyyadi.jampy.ui.screen.search.SearchScreen
import com.fyyadi.scan.ui.ScanScreen

object HomeNavigation {

    fun NavGraphBuilder.homeNavigation(
        navController: NavController,
        modifier: Modifier
    ) {
        composable<HomeRoutes.HomeScreen> {
            HomeScreen(
                modifier = modifier,
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

        composable<HomeRoutes.SearchScreen> {
            SearchScreen(
                modifier = modifier,
                onPlantClick = { idPlant ->
                    navController.navigate(HomeRoutes.DetailPlantScreen(idPlant))
                }
            )
        }

        composable<HomeRoutes.BookmarkScreen> {
            BookmarkScreen(
                modifier = modifier,
                onPlantClick = { idPlant ->
                    navController.navigate(HomeRoutes.DetailPlantScreen(idPlant))
                }
            )
        }
    }
}