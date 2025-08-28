package com.aptivist.tripsupiicsaapp.domain.contracts

import com.aptivist.tripsupiicsaapp.domain.models.CheckListEntryModel
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse

interface ICheckListEntryRepository {
    suspend fun getAllByTripId(tripId: Long): DomainResponse<List<CheckListEntryModel>>
    suspend fun upsert(tripId: Long, checkListEntry: CheckListEntryModel): DomainResponse<CheckListEntryModel>
    suspend fun delete(checkListEntryId: Long) : DomainResponse<Unit>
}