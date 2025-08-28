package com.aptivist.tripsupiicsaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationReceiver
import com.aptivist.tripsupiicsaapp.ui.views.HomeView
import com.aptivist.tripsupiicsaapp.ui.views.UpsertTripView
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(navigationReceiver: INavigationReceiver) {

    val navController = rememberNavController()

    LifecycleResumeEffect(Unit) {
        val navigationBusJob = lifecycleScope.launch {
            navigationReceiver.navigation.collect { action ->
                when (action) {
                    NavigationAction.NavigateBack -> navController.navigateUp()
                    is NavigationAction.NavigateTo -> {
                        if (action.clearBackStack) {
                            navController.navigate(action.route) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        } else {
                            navController.navigate(action.route)
                        }
                    }

                    is NavigationAction.NavigateToWithArgs -> {
                        val routeWithArgs = action.route + action.args.entries.joinToString(
                            prefix = "/",
                            separator = "/",
                            transform = { it.value }
                        )
                        if (action.clearBackStack) {
                            navController.navigate(action.route) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        } else {
                            navController.navigate(routeWithArgs)
                        }
                    }
                }
            }
        }

        onPauseOrDispose {
            navigationBusJob.cancel()
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME
    ) {
        composable(route = AppRoutes.HOME) { HomeView() }
        composable(
            route = "${AppRoutes.UPSERT_TRIP}/${AppRoutesArgs.TRIP_ID}",
            arguments = listOf(
                navArgument(AppRoutesArgs.TRIP_ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            val tripId = it.arguments?.getLong(AppRoutesArgs.TRIP_ID)

            tripId?.let {
                UpsertTripView(tripId = tripId)
            }

        }
    }

}