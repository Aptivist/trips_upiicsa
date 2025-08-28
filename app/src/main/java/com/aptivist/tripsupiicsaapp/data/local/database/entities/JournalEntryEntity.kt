package com.aptivist.tripsupiicsaapp.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "journal_entries",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["trip_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class JournalEntryEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "trip_id", index = true) val tripId: Long,
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") var createdAt: Date? = null,
    @ColumnInfo(name = "modified_at", defaultValue = "CURRENT_TIMESTAMP") var modifiedAt: Date? = null
)