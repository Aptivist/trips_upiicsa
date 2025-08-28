package com.aptivist.tripsupiicsaapp.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.aptivist.tripsupiicsaapp.data.local.database.entities.JournalEntryEntity

@Dao
interface JournalEntryDao {

    @Query("SELECT * FROM journal_entries ORDER BY id")
    suspend fun getAll(): List<JournalEntryEntity>

    @Query("DELETE FROM journal_entries")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<JournalEntryEntity>): List<Long>

    @Upsert
    suspend fun upsert(entry: JournalEntryEntity): Long

    @Query("""
        SELECT j.* FROM journal_entries j
        INNER JOIN trips t ON t.id = j.trip_id
        WHERE j.trip_id = :tripId
        ORDER BY j.id DESC
    """)
    suspend fun getByTripId(tripId: Long): List<JournalEntryEntity>

    @Query("""
        DELETE FROM journal_entries
        WHERE id = :journalEntryId
    """)
    suspend fun deleteById(journalEntryId: Long): Int
}