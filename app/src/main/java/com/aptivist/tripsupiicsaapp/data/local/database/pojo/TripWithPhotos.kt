package com.aptivist.tripsupiicsaapp.data.local.database.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.aptivist.tripsupiicsaapp.data.local.database.entities.PhotoEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripPhotoEntity


data class TripWithPhotos(
    @Embedded val trip: TripEntity,
    @Relation(
        parentColumn = "cover_photo_id",
        entityColumn = "id",
        entity = PhotoEntity::class
    )
    val coverPhoto : PhotoEntity? = null,
    @Relation(
        parentColumn = "id",
        entity = PhotoEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = TripPhotoEntity::class,
            parentColumn = "trip_id",
            entityColumn = "photo_id"
        )
    )
    val photos: List<PhotoEntity>
)
