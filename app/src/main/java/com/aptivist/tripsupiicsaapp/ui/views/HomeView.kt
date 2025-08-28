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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aptivist.tripsupiicsaapp.domain.models.LocationModel
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import com.aptivist.tripsupiicsaapp.ui.core.HomeTripCard
import com.aptivist.tripsupiicsaapp.ui.viewmodels.HomeViewModel

@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val trips = remember { viewModel.trips }

    HomeViewContent(
        trips = trips
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeViewContent(
    trips: List<TripModel>
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { },
                icon = { Icon(Icons.Filled.AddCircle, contentDescription = null) },
                text = { Text("Add Trip") }
            )
        },
        topBar = {
            TopAppBar(
                title = { Text("Home") },
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
                    modifier = Modifier.padding(vertical = 8.dp),
                    trip = item,
                )
            }
        }
    }
}

sealed class HomeViewActions {
    data class OnNavigateToUpsertTrip(val tripId: Long = -1) : HomeViewActions()
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
    )
}