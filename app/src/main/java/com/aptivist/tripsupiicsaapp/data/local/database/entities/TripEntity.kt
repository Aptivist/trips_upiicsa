package com.aptivist.tripsupiicsaapp.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "trips",
    foreignKeys = [
        ForeignKey(
            entity = PhotoEntity::class,
            parentColumns = ["id"],
            childColumns = ["cover_photo_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ])
data class TripEntity(
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "destination") val destination : String,
    @ColumnInfo(name = "cover_photo_id", index = true) val coverPhotoId : Long? = null,
    @ColumnInfo(name = "start_date") val startDate : Date,
    @ColumnInfo(name = "end_date") val endDate : Date,
    @ColumnInfo(name = "latitude") val latitude : Double? = null,
    @ColumnInfo(name = "longitude") val longitude : Double? = null,
    @ColumnInfo(name = "notes") val notes : String? = null,
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") val createdAt: Date? = null,
    @ColumnInfo(name = "modified_at", defaultValue = "CURRENT_TIMESTAMP") val modifiedAt: Date? = null
)
