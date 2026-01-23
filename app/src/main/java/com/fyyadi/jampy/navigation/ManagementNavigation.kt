package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fyyadi.jampy.route.ManagementRoutes
import com.fyyadi.management.presentation.ui.plant.AddPlantScreen
import com.fyyadi.management.presentation.ui.plant.EditPlantScreen
import com.fyyadi.management.presentation.ui.plant.PlantManagementScreen
import com.fyyadi.management.presentation.ui.users.UsersManagementScreen

object ManagementNavigation {

    fun NavGraphBuilder.managementNavigation(
        navController: NavController,
        modifier: Modifier
    ) {
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

        composable<ManagementRoutes.PlantManagementScreen> {
            PlantManagementScreen(
                modifier = modifier,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddNewPlantClick = {
                    navController.navigate(ManagementRoutes.AddPlantScreen)
                },
                onEditPlantClick = { idPlant ->
                    navController.navigate(ManagementRoutes.EditPlantScreen(idPlant))
                }
            )
        }

        composable<ManagementRoutes.AddPlantScreen> {
            AddPlantScreen(
                modifier = modifier,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable<ManagementRoutes.EditPlantScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<ManagementRoutes.EditPlantScreen>()
            EditPlantScreen(
                modifier = modifier,
                plantId = args.plantId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }

}
