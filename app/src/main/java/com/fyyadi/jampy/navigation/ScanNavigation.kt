package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fyyadi.domain.model.PlantLabel
import com.fyyadi.jampy.route.ScanRoutes
import com.fyyadi.scan.presentation.ui.CameraScreen
import com.fyyadi.scan.presentation.ui.ResultScanScreen
import com.fyyadi.scan.presentation.ui.ScanScreen

object ScanNavigation {

    fun NavGraphBuilder.scanNavigation(
        navController: NavController,
        modifier: Modifier
    ) {
        composable<ScanRoutes.ScanScreen> { backStackEntry ->
            val capturedImageUri = backStackEntry.savedStateHandle.get<String>("captured_image_uri")
            ScanScreen(
                modifier = modifier,
                capturedImageUri = capturedImageUri,
                onCameraClick = { onResult, onCancel ->
                    navController.navigate(ScanRoutes.CameraScreen)
                },
                onResultClassification = { plantLabels, imageResultUri, userEmail ->
                    val labelsJson = plantLabels.joinToString(separator = "|") {
                        "${it.name},${it.displayName},${it.confidence}"
                    }
                    navController.navigate(
                        ScanRoutes.ResultScanScreen(plantLabels = labelsJson, imageResultUri, userEmail)
                    )
                }
            )
        }

        composable<ScanRoutes.CameraScreen> {
            CameraScreen(
                modifier = modifier,
                onResult = { uri ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("captured_image_uri", uri.toString())
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        composable<ScanRoutes.ResultScanScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<ScanRoutes.ResultScanScreen>()
            val plantResult = args.plantLabels.split("|").mapNotNull { item ->
                val parts = item.split(",")
                if (parts.size == 3) {
                    PlantLabel(
                        name = parts[0],
                        displayName = parts[1],
                        confidence = parts[2].toFloatOrNull() ?: 0f
                    )
                } else null
            }

            ResultScanScreen(
                modifier = modifier,
                plantResult = plantResult[0],
                imageResultUri = args.imageResultUri,
                userEmail = args.userEmail,
                onBackClick = { navController.popBackStack() }
            )
        }

    }
}