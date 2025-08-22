package com.aptivist.tripsupiicsaapp.data.local.repository

import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripDao
import com.aptivist.tripsupiicsaapp.data.local.database.toDomainModel
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.TripModel

class DatabaseTripRepository(private val tripDao: TripDao) : ITripRepository {

    override suspend fun getAll(): DomainResponse<List<TripModel>> {
        try {
            val trips = tripDao.getAll()
            return DomainResponse.Success(trips.map { tripEntity -> tripEntity.toDomainModel() })
        } catch (e: Exception) {
            return DomainResponse.Error("Error fetching trips: ${e.message}", code = 500)
        }
    }

}