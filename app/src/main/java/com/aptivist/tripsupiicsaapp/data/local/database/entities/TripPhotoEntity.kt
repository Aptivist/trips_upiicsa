package com.aptivist.tripsupiicsaapp.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "trip_photos",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["trip_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PhotoEntity::class,
            parentColumns = ["id"],
            childColumns = ["photo_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["trip_id", "photo_id"], unique = true)
    ]
)
data class TripPhotoEntity(
    @ColumnInfo(name = "trip_id") val tripId: Long,
    @ColumnInfo(name = "photo_id") val photoId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") val createdAt: Date? = null,
    @ColumnInfo(name = "modified_at", defaultValue = "CURRENT_TIMESTAMP") val modifiedAt: Date? = null
)