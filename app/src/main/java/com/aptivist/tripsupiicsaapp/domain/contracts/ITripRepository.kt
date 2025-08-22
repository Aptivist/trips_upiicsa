package com.aptivist.tripsupiicsaapp.domain.contracts

import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.TripModel

interface ITripRepository {
    suspend fun getAll(): DomainResponse<List<TripModel>>
}