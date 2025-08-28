package com.aptivist.tripsupiicsaapp.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aptivist.tripsupiicsaapp.data.local.database.entities.PhotoEntity

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos ORDER BY id")
    suspend fun getAll(): List<PhotoEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>): List<Long>
    @Query("DELETE FROM photos")
    suspend fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: PhotoEntity): Long
}