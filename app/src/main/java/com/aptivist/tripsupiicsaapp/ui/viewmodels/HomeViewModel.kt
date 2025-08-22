package com.aptivist.tripsupiicsaapp.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.LocationModel
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val tripRepository: ITripRepository,
) {

    private val _trips = mutableStateListOf<TripModel>(
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
    val trips: List<TripModel> get() = _trips

//    fun loadTrips() {
//        viewModelScope.launch { tripRepository.getAll() }
//    }

}