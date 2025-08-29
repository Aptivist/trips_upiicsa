package com.aptivist.tripsupiicsaapp.domain.contracts

import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import com.aptivist.tripsupiicsaapp.domain.models.TripPhotoModel

interface ITripRepository {
    suspend fun getAll(): DomainResponse<List<TripModel>>
    suspend fun getById(tripId: Long): DomainResponse<TripModel>
    suspend fun upsert(trip: TripModel): DomainResponse<TripModel>
    suspend fun delete(tripId: Long): DomainResponse<Unit>

    suspend fun getTripPhotos(tripId: Long): DomainResponse<List<TripPhotoModel>>
}