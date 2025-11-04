package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fyyadi.domain.model.PlantLabel
import com.fyyadi.jampy.route.ManagementRoutes
import com.fyyadi.jampy.route.ScanRoutes
import com.fyyadi.management.presentation.ui.plant.PlantManagementScreen
import com.fyyadi.scan.presentation.ui.CameraScreen
import com.fyyadi.scan.presentation.ui.ResultScanScreen
import com.fyyadi.scan.presentation.ui.ScanScreen

object ManagementNavigation {

    fun NavGraphBuilder.managementNavigation(
        navController: NavController,
        modifier: Modifier
    ) {
        composable<ManagementRoutes.PlantManagementScreen> {
            PlantManagementScreen (
                modifier = modifier,
                onPlantClick = {

                }
            )
        }

    }
}