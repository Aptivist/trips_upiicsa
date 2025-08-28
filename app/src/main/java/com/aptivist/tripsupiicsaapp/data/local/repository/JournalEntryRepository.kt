package com.aptivist.tripsupiicsaapp.data.local.repository

import com.aptivist.tripsupiicsaapp.data.local.database.daos.JournalEntryDao
import com.aptivist.tripsupiicsaapp.data.local.database.toDomainModel
import com.aptivist.tripsupiicsaapp.data.local.database.toEntity
import com.aptivist.tripsupiicsaapp.domain.contracts.IJournalEntryRepository
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.JournalEntryModel

import javax.inject.Inject

class JournalEntryRepository @Inject constructor(
    private val journalEntryDao: JournalEntryDao,
) : IJournalEntryRepository {

    override suspend fun upsert(
        tripId: Long,
        journalEntry: JournalEntryModel
    ): DomainResponse<JournalEntryModel> {
        return try {
            val journalEntryEntity = journalEntry.toEntity(tripId)
            val upsertResult = journalEntryDao.upsert(journalEntryEntity)
            val updatedJournalEntry = if (upsertResult == -1L) {
                journalEntry.copy()
            } else {
                journalEntry.copy(id = upsertResult)
            }
            DomainResponse.Success(updatedJournalEntry)
        } catch (e: Exception) {
            DomainResponse.Error("Error upserting journal entry: ${e.message}", code = 500)
        }
    }

    override suspend fun delete(
        journalEntryId: Long
    ): DomainResponse<Unit> {
        return try {
            journalEntryDao.deleteById(journalEntryId)
            DomainResponse.Success(Unit)
        } catch (e: Exception) {
            DomainResponse.Error("Error deleting journal entry: ${e.message}", code = 500)
        }
    }

    override suspend fun getByTripId(tripId: Long): DomainResponse<List<JournalEntryModel>> {
        return try {
            val journalEntries = journalEntryDao.getByTripId(tripId).map { it.toDomainModel() }
            DomainResponse.Success(journalEntries)
        } catch (e: Exception) {
            DomainResponse.Error("Error fetching journal entries by trip ID: ${e.message}", code = 500)
        }
    }

}
