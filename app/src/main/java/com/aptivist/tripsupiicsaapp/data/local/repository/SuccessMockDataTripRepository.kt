package com.aptivist.tripsupiicsaapp.data.local.repository

import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.LocationModel
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import javax.inject.Inject

class SuccessMockDataTripRepository @Inject constructor() : ITripRepository {

    override suspend fun getAll(): DomainResponse<List<TripModel>> {
        return DomainResponse.Success(
            listOf(
                TripModel(
                    id = 1,
                    name = "Trip to CDMX",
                    destination = "Mexico",
                    startDate = "",
                    endDate = "",
                    location = LocationModel(latitude = 0.0, longitude = 0.0),
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
                    location = LocationModel(latitude = 0.0, longitude = 0.0),
                    notes = "",
                    coverImageUrl = "",
                    photosUris = emptyList()
                ),
                TripModel(
                    id = 3,
                    name = "Trip to Tijuana",
                    destination = "Mexico",
                    startDate = "",
                    endDate = "",
                    location = LocationModel(latitude = 0.0, longitude = 0.0),
                    notes = "",
                    coverImageUrl = "",
                    photosUris = emptyList()
                )
            )
        )
    }
}