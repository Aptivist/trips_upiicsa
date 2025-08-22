package com.aptivist.tripsupiicsaapp.domain.models

data class TripModel(
    val id: Long? = null,
    val name: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val location: LocationModel,
    val notes: String,
    val coverImageUrl: String,
    val photosUris: List<String> = emptyList()
)
