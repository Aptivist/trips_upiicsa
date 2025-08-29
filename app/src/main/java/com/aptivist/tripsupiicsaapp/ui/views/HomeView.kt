package com.aptivist.tripsupiicsaapp.ui.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.domain.models.LocationModel
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import com.aptivist.tripsupiicsaapp.ui.core.HomeTripCard
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutes
import com.aptivist.tripsupiicsaapp.ui.viewmodels.HomeViewModel

@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val trips = remember { viewModel.trips }

    HomeViewContent(
        trips = trips,
        homeViewActions = { action ->
            when (action) {
                is HomeViewActions.OnNavigateToUpsertTrip -> {
                    viewModel.navigateTo(
                        AppRoutes.UPSERT_TRIP,
                        tripId = action.tripId,
                    )
                }

                is HomeViewActions.OnNavigateToTripItemDetails -> {
                    viewModel.navigateTo(
                        AppRoutes.TRIP_DETAILS,
                        action.tripId
                    )
                }

                is HomeViewActions.RemoveTripItem -> {
                    viewModel.removeTrip(action.tripId)
                }
                is HomeViewActions.ToggleDrawer -> {

                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeViewContent(
    trips: List<TripModel>,
    homeViewActions: (HomeViewActions) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { homeViewActions.invoke(HomeViewActions.OnNavigateToUpsertTrip()) },
                icon = { Icon(Icons.Filled.AddCircle, contentDescription = null) },
                text = { Text(stringResource(R.string.add_trip)) }
            )
        },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home)) },
                navigationIcon = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(items = trips) { item ->
                HomeTripCard(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .animateItem(),
                    trip = item,
                    onEditTripItem = {
                        homeViewActions.invoke(
                            HomeViewActions.OnNavigateToUpsertTrip(
                                item.id ?: -1
                            )
                        )
                    },
                    onRemoveTripItem = {
                        item.id?.let {
                            homeViewActions.invoke(
                                HomeViewActions.RemoveTripItem(it)
                            )
                        }
                    },
                    onNavigateToTripItemDetails = {
                        item.id?.let {
                            homeViewActions.invoke(
                                HomeViewActions.OnNavigateToTripItemDetails(
                                    item.id
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}

sealed class HomeViewActions {
    data class OnNavigateToUpsertTrip(val tripId: Long = -1) : HomeViewActions()
    data class OnNavigateToTripItemDetails(val tripId: Long) : HomeViewActions()
    data class ToggleDrawer(val open: Boolean) : HomeViewActions()
    data class RemoveTripItem(val tripId: Long) : HomeViewActions()
}

@Preview
@Composable
private fun HomeViewPreview() {
    HomeViewContent(
        trips = listOf(
            TripModel(
                id = 1,
                name = "Trip to CDMX",
                destination = "Mexico",
                startDate = "",
                endDate = "",
                location = LocationModel(
                    latitude = 0.0,
                    longitude = 0.0,
                ),
                notes = "",
                coverImageUrl = "",
                photosUris = emptyList()
            ),
            TripModel(
                id = 2,
                name = "Trip to Monterrey",
                destination = "Mexico",
                startDate = "",
                endDate = "",
                location = LocationModel(
                    latitude = 0.0,
                    longitude = 0.0,
                ),
                notes = "",
                coverImageUrl = "",
                photosUris = emptyList()
            ),
            TripModel(
                id = 2,
                name = "Trip to Tijuana",
                destination = "Mexico",
                startDate = "",
                endDate = "",
                location = LocationModel(
                    latitude = 0.0,
                    longitude = 0.0,
                ),
                notes = "",
                coverImageUrl = "",
                photosUris = emptyList()
            )
        )
    ) {
        when (it) {
            is HomeViewActions.OnNavigateToUpsertTrip -> {}
            is HomeViewActions.OnNavigateToTripItemDetails -> {}
            is HomeViewActions.RemoveTripItem -> {}
            is HomeViewActions.ToggleDrawer -> {}
        }
    }
}