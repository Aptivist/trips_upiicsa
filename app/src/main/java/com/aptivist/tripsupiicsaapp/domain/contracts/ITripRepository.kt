package com.aptivist.tripsupiicsaapp.domain.contracts

import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.TripModel

interface ITripRepository {
    suspend fun getAll(): DomainResponse<List<TripModel>>
    suspend fun getById(tripId: Long): DomainResponse<TripModel>
    suspend fun upsert(trip: TripModel): DomainResponse<TripModel>
    suspend fun delete(tripId: Long): DomainResponse<Unit>

    suspend fun getTripsWithPhotos(): DomainResponse<List<TripModel>>
}