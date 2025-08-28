package com.aptivist.tripsupiicsaapp.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.aptivist.tripsupiicsaapp.data.local.database.entities.CheckListEntryEntity

@Dao
interface CheckListEntryDao {

    @Query("SELECT * FROM checklist_entries ORDER BY id")
    suspend fun getAll(): List<CheckListEntryEntity>

    @Query("DELETE FROM checklist_entries")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<CheckListEntryEntity>): List<Long>

    @Upsert
    suspend fun upsert(item: CheckListEntryEntity): Long

    @Query("""
        SELECT c.* FROM checklist_entries c
        INNER JOIN trips t ON t.id = c.trip_id
        WHERE c.trip_id = :tripId
        ORDER BY c.id DESC
    """)
    suspend fun getByTripId(tripId: Long): List<CheckListEntryEntity>

    @Query("""
        DELETE FROM checklist_entries
        WHERE id = :checkListEntryId
    """)
    suspend fun deleteById(checkListEntryId: Long): Int
}