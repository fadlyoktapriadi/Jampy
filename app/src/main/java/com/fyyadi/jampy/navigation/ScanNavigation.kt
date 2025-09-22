package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fyyadi.jampy.route.HomeRoutes
import com.fyyadi.jampy.route.ScanRoutes
import com.fyyadi.jampy.ui.screen.bookmark.BookmarkScreen
import com.fyyadi.jampy.ui.screen.detail.DetailPlantScreen
import com.fyyadi.jampy.ui.screen.home.HomeScreen
import com.fyyadi.jampy.ui.screen.search.SearchScreen
import com.fyyadi.scan.ui.CameraScreen
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

        composable<ScanRoutes.ResultScanScreen> {
            // Implement ResultScanScreen
        }

        composable<ScanRoutes.ResultScanScreen> {
        }

    }
}