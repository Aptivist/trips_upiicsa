package com.aptivist.tripsupiicsaapp.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aptivist.tripsupiicsaapp.data.local.database.entities.PhotoEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripPhotoEntity

@Dao
interface TripPhotoDao {

    @Query("SELECT * FROM trip_photos ORDER BY id")
    suspend fun getAll(): List<TripPhotoEntity>

    @Query("DELETE FROM trip_photos")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<TripPhotoEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun link(link: TripPhotoEntity): Long

    @Query("""
        DELETE FROM trip_photos
        WHERE trip_id = :tripId
          AND photo_id = :photoId
    """)
    suspend fun unlink(tripId: Long, photoId: Long): Int

    @Query("""
        SELECT p.* FROM photos p
        INNER JOIN trip_photos tp ON tp.photo_id = p.id
        INNER JOIN trips t ON t.id = tp.trip_id
        WHERE tp.trip_id = :tripId
        ORDER BY p.id DESC
    """)
    suspend fun getPhotosForTrip(tripId: Long): List<PhotoEntity>

    @Query("""
            SELECT ph.* FROM trip_photos p
            INNER JOIN photos ph ON p.photo_id = ph.id
            WHERE trip_id = :tripId AND ph.name = :photoUri
            LIMIT 1
    """)
    suspend fun photoExistsInTrip(tripId: Long, photoUri: String): PhotoEntity?

    @Query("""
            SELECT * FROM photos
            WHERE name = :photoUri
            LIMIT 1
    """)
    suspend fun photoExists(photoUri: String): PhotoEntity?
}