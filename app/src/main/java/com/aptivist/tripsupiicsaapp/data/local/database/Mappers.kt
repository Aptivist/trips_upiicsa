package com.aptivist.tripsupiicsaapp.data.local.database

import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripEntity
import com.aptivist.tripsupiicsaapp.domain.models.LocationModel
import com.aptivist.tripsupiicsaapp.domain.models.TripModel

fun TripEntity.toDomainModel(): TripModel {
    return TripModel(
        id = id,
        name = name,
        destination = destination,
        startDate = startDate.toString(),
        endDate = endDate.toString(),
        location = LocationModel(latitude ?: 0.0, longitude ?: 0.0),
        notes = notes ?: "",
        coverImageUrl = "",
        photosUris = emptyList()
    )
}