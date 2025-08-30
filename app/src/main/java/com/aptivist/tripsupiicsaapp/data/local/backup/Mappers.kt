package com.aptivist.tripsupiicsaapp.data.local.backup

import com.aptivist.tripsupiicsaapp.data.local.database.entities.CheckListEntryEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.JournalEntryEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.PhotoEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripPhotoEntity
import java.util.Date

fun TripEntity.toDump() = TripDump(
    id!!, name, destination, coverPhotoId,
    startDate.time, endDate.time, latitude, longitude, notes,
    createdAt?.time, modifiedAt?.time
)

fun JournalEntryEntity.toDump() = JournalEntryDump(
    id!!, title, content, tripId, createdAt?.time, modifiedAt?.time
)

fun CheckListEntryEntity.toDump() = CheckListEntryDump(
    id!!, name, isChecked, tripId, createdAt?.time, modifiedAt?.time
)

fun PhotoEntity.toDump() = PhotoDump(
    id!!, name, caption, createdAt?.time, modifiedAt?.time
)

fun TripPhotoEntity.toDump() = TripPhotoDump(
    id!!, tripId, photoId, createdAt?.time, modifiedAt?.time
)

fun TripDump.toEntity() = TripEntity(
    name = name,
    destination = destination,
    coverPhotoId = coverPhotoId,
    startDate = Date(startDate),
    endDate = Date(endDate),
    latitude = latitude,
    longitude = longitude,
    notes = notes,
    id = id,
    createdAt = createdAt?.let { Date(it) },
    modifiedAt = modifiedAt?.let { Date(it) }
)

fun JournalEntryDump.toEntity() = JournalEntryEntity(
    title = title,
    content = content,
    tripId = tripId,
    id = id,
    createdAt = createdAt?.let { Date(it) },
    modifiedAt = modifiedAt?.let { Date(it) }
)

fun CheckListEntryDump.toEntity() = CheckListEntryEntity(
    name = name,
    isChecked = isChecked,
    tripId = tripId,
    id = id,
    createdAt = createdAt?.let { Date(it) },
    modifiedAt = modifiedAt?.let { Date(it) }
)

fun PhotoDump.toEntity() = PhotoEntity(
    name = name,
    caption = caption,
    id = id,
    createdAt = createdAt?.let { Date(it) },
    modifiedAt = modifiedAt?.let { Date(it) }
)

fun TripPhotoDump.toEntity() = TripPhotoEntity(
    tripId = tripId,
    photoId = photoId,
    id = id,
    createdAt = createdAt?.let { Date(it) },
    modifiedAt = modifiedAt?.let { Date(it) }
)