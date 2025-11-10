package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fyyadi.jampy.route.ManagementRoutes
import com.fyyadi.management.presentation.ui.plant.PlantManagementScreen
import com.fyyadi.management.presentation.ui.users.UsersManagementScreen

object ManagementNavigation {

    fun NavGraphBuilder.managementNavigation(
        navController: NavController,
        modifier: Modifier
    ) {
        composable<ManagementRoutes.PlantManagementScreen> {
            PlantManagementScreen (
                modifier = modifier,
                onPlantClick = {

                },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable<ManagementRoutes.UsersManagementScreen> {
            UsersManagementScreen(
                modifier = modifier,
                onBackClick = {
                    navController.popBackStack()
                },
                onUserClick = {

                }
            )
        }

    }
}