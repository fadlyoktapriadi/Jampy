package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fyyadi.domain.model.PlantLabel
import com.fyyadi.jampy.route.HomeRoutes
import com.fyyadi.jampy.route.ScanRoutes
import com.fyyadi.jampy.ui.screen.bookmark.BookmarkScreen
import com.fyyadi.jampy.ui.screen.detail.DetailPlantScreen
import com.fyyadi.jampy.ui.screen.home.HomeScreen
import com.fyyadi.jampy.ui.screen.search.SearchScreen
import com.fyyadi.scan.ui.CameraScreen
import com.fyyadi.scan.ui.ResultScanScreen
import com.fyyadi.scan.ui.ScanScreen
import kotlin.collections.set
import kotlin.text.get
import kotlin.toString

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
                onResultClassification = { plantLabels ->
                    val labelsJson = plantLabels.joinToString(separator = "|") {
                        "${it.name},${it.displayName},${it.confidence}"
                    }
                    navController.navigate(
                        ScanRoutes.ResultScanScreen(plantLabels = labelsJson)
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
            val plantLabels = args.plantLabels.split("|").mapNotNull { item ->
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
                plantLabels = plantLabels,
                onBackClick = { navController.popBackStack() }
            )
        }

    }
}