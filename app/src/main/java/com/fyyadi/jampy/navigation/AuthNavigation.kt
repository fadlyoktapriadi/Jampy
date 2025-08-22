package com.fyyadi.jampy.navigation

import android.app.Activity
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fyyadi.jampy.route.AuthRoutes
import com.fyyadi.jampy.route.HomeRoutes
import com.fyyadi.jampy.ui.screen.LoginScreen
import com.fyyadi.jampy.ui.screen.SplashScreen

object AuthNavigation {
    fun NavGraphBuilder.authNavigation(
        navController: NavController,
        modifier: Modifier
    ) {
        composable<AuthRoutes.SplashScreen> {
            SplashScreen(
                onLoggedNavigate = {
                    navController.navigate(HomeRoutes.HomeScreen) {
                        popUpTo(AuthRoutes.SplashScreen) {
                            inclusive = true
                        }
                    }
                },
                onLoginNavigate = {
                    navController.navigate(AuthRoutes.LoginScreen) {
                        popUpTo(AuthRoutes.SplashScreen) {
                            inclusive = true
                        }
                    }
                },

                modifier = modifier
            )
        }
        composable<AuthRoutes.LoginScreen> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(HomeRoutes.HomeScreen) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )

        }
    }
}