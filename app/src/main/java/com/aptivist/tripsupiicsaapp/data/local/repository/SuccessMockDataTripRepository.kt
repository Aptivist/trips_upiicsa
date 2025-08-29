package com.aptivist.tripsupiicsaapp.data.local.repository

import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.LocationModel
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import com.aptivist.tripsupiicsaapp.domain.models.TripPhotoModel
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
                    coverImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Sobrevuelos_CDMX_HJ2A4913_%2825514321687%29_%28cropped%29.jpg/960px-Sobrevuelos_CDMX_HJ2A4913_%2825514321687%29_%28cropped%29.jpg",
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
                    coverImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/View_of_Monterrey_%282015%29.jpg/1200px-View_of_Monterrey_%282015%29.jpg",
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

    override suspend fun getById(tripId: Long): DomainResponse<TripModel> {
        return DomainResponse.Success(
            TripModel(
                id = 1,
                name = "Trip to CDMX",
                destination = "Mexico",
                startDate = "",
                endDate = "",
                location = LocationModel(latitude = 0.0, longitude = 0.0),
                notes = "",
                coverImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Sobrevuelos_CDMX_HJ2A4913_%2825514321687%29_%28cropped%29.jpg/960px-Sobrevuelos_CDMX_HJ2A4913_%2825514321687%29_%28cropped%29.jpg",
                photosUris = emptyList()
            )
        )
    }

    override suspend fun upsert(trip: TripModel): DomainResponse<TripModel> {
        return DomainResponse.Success(
            TripModel(
                id = 1,
                name = "Trip to CDMX",
                destination = "Mexico",
                startDate = "",
                endDate = "",
                location = LocationModel(latitude = 0.0, longitude = 0.0),
                notes = "",
                coverImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Sobrevuelos_CDMX_HJ2A4913_%2825514321687%29_%28cropped%29.jpg/960px-Sobrevuelos_CDMX_HJ2A4913_%2825514321687%29_%28cropped%29.jpg",
                photosUris = emptyList()
            )
        )
    }

    override suspend fun delete(tripId: Long): DomainResponse<Unit> {
        return DomainResponse.Success(Unit)
    }

    override suspend fun getTripPhotos(tripId: Long): DomainResponse<List<TripPhotoModel>> {
        return DomainResponse.Success(emptyList())
    }
}