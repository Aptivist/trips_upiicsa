package com.aptivist.tripsupiicsaapp.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aptivist.tripsupiicsaapp.data.local.database.converters.DateConverter
import com.aptivist.tripsupiicsaapp.data.local.database.daos.CheckListEntryDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.JournalEntryDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.PhotoDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripPhotoDao
import com.aptivist.tripsupiicsaapp.data.local.database.entities.CheckListEntryEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.JournalEntryEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.PhotoEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripPhotoEntity

@TypeConverters(DateConverter::class)
@Database(
    entities = [
        PhotoEntity::class,
        TripEntity::class,
        TripPhotoEntity::class,
        JournalEntryEntity::class,
        CheckListEntryEntity::class
    ],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3)
    ]
)
abstract class TripsDatabase : RoomDatabase() {

    abstract fun tripDao() : TripDao

    abstract fun tripPhotoDao() : TripPhotoDao
    abstract fun photoDao() : PhotoDao

    abstract fun journalEntryDao() : JournalEntryDao

    abstract fun checkListEntryDao() : CheckListEntryDao

}