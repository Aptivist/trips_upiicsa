package com.aptivist.tripsupiicsaapp.data.local.database

import com.aptivist.tripsupiicsaapp.data.local.database.entities.CheckListEntryEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.JournalEntryEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.PhotoEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripEntity
import com.aptivist.tripsupiicsaapp.data.local.database.pojo.TripWithPhotos
import com.aptivist.tripsupiicsaapp.domain.models.CheckListEntryModel
import com.aptivist.tripsupiicsaapp.domain.models.JournalEntryModel
import com.aptivist.tripsupiicsaapp.domain.models.LocationModel
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import com.aptivist.tripsupiicsaapp.domain.models.TripPhotoModel
import com.aptivist.tripsupiicsaapp.ui.utils.formatDate
import com.aptivist.tripsupiicsaapp.ui.utils.parseToDate

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

fun TripWithPhotos.toDomainModel(): TripModel =
    TripModel(
        id = this.trip.id,
        name = this.trip.name,
        destination = this.trip.destination,
        startDate = formatDate(this.trip.startDate),
        endDate = formatDate(this.trip.endDate),
        location = LocationModel(this.trip.latitude ?: 0.0, this.trip.longitude ?: 0.0),
        notes = this.trip.notes ?: "",
        coverImageUrl = this.coverPhoto?.name ?: "",
        photosUris = this.photos.map { it.name }
    )


fun JournalEntryEntity.toDomainModel(): JournalEntryModel =
    JournalEntryModel(
        id = this.id,
        title = this.title,
        content = this.content
    )

fun JournalEntryModel.toEntity(tripId: Long): JournalEntryEntity =
    JournalEntryEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        tripId = tripId
    )

fun PhotoEntity.toDomainModel(): TripPhotoModel =
    TripPhotoModel(
        id = this.id,
        url = this.name,
    )

fun CheckListEntryEntity.toDomainModel(): CheckListEntryModel =
    CheckListEntryModel(
        id = this.id,
        name = this.name,
        isChecked = this.isChecked
    )

fun CheckListEntryModel.toEntity(tripId: Long): CheckListEntryEntity =
    CheckListEntryEntity(
        id = this.id,
        name = this.name,
        isChecked = this.isChecked,
        tripId = tripId
    )

fun TripModel.toEntity(): TripEntity =
    TripEntity(
        id = this.id,
        name = this.name,
        destination = this.destination,
        startDate = parseToDate(this.startDate),
        endDate = parseToDate(this.endDate),
        latitude = this.location.latitude,
        longitude = this.location.longitude,
        notes = this.notes,
        coverPhotoId = null
    )