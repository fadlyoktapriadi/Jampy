package com.fyyadi.jampy.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fyyadi.jampy.route.SearchRoutes
import com.fyyadi.jampy.ui.screen.search.SearchScreen

object SearchNavigation {

    fun NavGraphBuilder.searchNavigation(
        navController: NavController,
        modifier: Modifier
    ) {
        composable<SearchRoutes.SearchScreen> {
            SearchScreen(
                modifier = modifier,
            )
        }
    }
}