package com.aptivist.tripsupiicsaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aptivist.tripsupiicsaapp.ui.views.HomeView

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME
    ) {
        composable(route = AppRoutes.HOME) { HomeView() }
    }

}