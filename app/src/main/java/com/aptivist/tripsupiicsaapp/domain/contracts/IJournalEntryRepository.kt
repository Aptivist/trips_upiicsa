package com.aptivist.tripsupiicsaapp.domain.contracts

import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.JournalEntryModel

interface IJournalEntryRepository {
    suspend fun upsert(tripId: Long, journalEntry: JournalEntryModel): DomainResponse<JournalEntryModel>
    suspend fun delete(journalEntryId: Long): DomainResponse<Unit>
    suspend fun getByTripId(tripId: Long): DomainResponse<List<JournalEntryModel>>
}