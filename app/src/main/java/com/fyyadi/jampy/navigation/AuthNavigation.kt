package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fyyadi.auth.presentation.ui.login.LoginScreen
import com.fyyadi.auth.presentation.ui.splashscreen.SplashScreen
import com.fyyadi.jampy.route.AuthRoutes
import com.fyyadi.jampy.route.HomeRoutes

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