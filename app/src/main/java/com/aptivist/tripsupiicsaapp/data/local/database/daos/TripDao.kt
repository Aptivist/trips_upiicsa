package com.aptivist.tripsupiicsaapp.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripEntity
import com.aptivist.tripsupiicsaapp.data.local.database.pojo.TripWithPhotos
import java.util.Date

@Dao
interface TripDao {

    @Query("SELECT * FROM trips ORDER BY id")
    suspend fun getAll(): List<TripEntity>

    @Query("DELETE FROM trips")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM trips")
    suspend fun getAllTripsWithPhotos(): List<TripWithPhotos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<TripEntity>): List<Long>

    @Upsert
    suspend fun upsert(trip: TripEntity): Long

    @Query("UPDATE trips Set cover_photo_id = :photoId, modified_at = :modifiedAt WHERE id = :tripId")
    suspend fun setCoverPhotoId(tripId: Long, photoId: Long?, modifiedAt: Date = Date()) : Int

    @Query("DELETE FROM trips WHERE id = :tripId")
    suspend fun deleteTripById(tripId: Long) : Int
}