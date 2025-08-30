package com.aptivist.tripsupiicsaapp.data.local.backup

import kotlinx.serialization.Serializable

@Serializable
data class BackupPayload(
    val version: Int = 1,
    val exportedAtEpochMs: Long,
    val trips: List<TripDump>,
    val journalEntries: List<JournalEntryDump>,
    val checklistEntries: List<CheckListEntryDump>,
    val photos: List<PhotoDump>,
    val tripPhotos: List<TripPhotoDump>)


@Serializable data class TripDump(
    val id: Long,
    val name: String,
    val destination: String,
    val coverPhotoId: Long? = null,
    val startDate: Long, // epoch ms
    val endDate: Long,   // epoch ms
    val latitude: Double? = null,
    val longitude: Double? = null,
    val notes: String? = null,
    val createdAt: Long? = null,
    val modifiedAt: Long? = null
)

@Serializable data class JournalEntryDump(
    val id: Long,
    val title: String,
    val content: String,
    val tripId: Long,
    val createdAt: Long? = null,
    val modifiedAt: Long? = null
)

@Serializable data class CheckListEntryDump(
    val id: Long,
    val name: String,
    val isChecked: Boolean,
    val tripId: Long,
    val createdAt: Long? = null,
    val modifiedAt: Long? = null
)

@Serializable data class PhotoDump(
    val id: Long,
    val name: String,
    val caption: String? = null,
    val createdAt: Long? = null,
    val modifiedAt: Long? = null
)

@Serializable data class TripPhotoDump(
    val id: Long,
    val tripId: Long,
    val photoId: Long,
    val createdAt: Long? = null,
    val modifiedAt: Long? = null
)