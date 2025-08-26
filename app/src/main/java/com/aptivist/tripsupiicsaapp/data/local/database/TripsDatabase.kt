package com.aptivist.tripsupiicsaapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aptivist.tripsupiicsaapp.data.local.database.converters.DateConverter
import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripDao
import com.aptivist.tripsupiicsaapp.data.local.database.entities.PhotoEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripPhotoEntity

@TypeConverters(DateConverter::class)
@Database(
    entities = [
        PhotoEntity::class,
        TripEntity::class,
        TripPhotoEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class TripsDatabase : RoomDatabase() {

    abstract fun tripDao() : TripDao

}