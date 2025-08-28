package com.aptivist.tripsupiicsaapp.data.local.repository

import com.aptivist.tripsupiicsaapp.data.local.database.daos.CheckListEntryDao
import com.aptivist.tripsupiicsaapp.data.local.database.toDomainModel
import com.aptivist.tripsupiicsaapp.data.local.database.toEntity
import com.aptivist.tripsupiicsaapp.domain.contracts.ICheckListEntryRepository
import com.aptivist.tripsupiicsaapp.domain.models.CheckListEntryModel
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import javax.inject.Inject

class CheckListEntryRepository @Inject constructor(
    private val checkListEntryDao: CheckListEntryDao
) : ICheckListEntryRepository {

    override suspend fun getAllByTripId(tripId: Long): DomainResponse<List<CheckListEntryModel>> {
        return try {
            val checkListEntries = checkListEntryDao.getByTripId(tripId)
                .map { it.toDomainModel() }
            DomainResponse.Success(checkListEntries)
        } catch (e: Exception) {
            DomainResponse.Error("Error fetching checklist entries: ${e.message}", code = 500)
        }
    }


    override suspend fun upsert(tripId: Long, checkListEntry: CheckListEntryModel): DomainResponse<CheckListEntryModel> {
        return try {
            val checkListEntryEntity = checkListEntry.toEntity(tripId)
            val upsertResult = checkListEntryDao.upsert(checkListEntryEntity)
            val updatedCheckListEntry = if (upsertResult == -1L) {
                checkListEntry.copy()
            } else {
                checkListEntry.copy(id = upsertResult)
            }
            DomainResponse.Success(updatedCheckListEntry)
        } catch (e: Exception) {
            DomainResponse.Error("Error upserting checklist entry: ${e.message}", code = 500)
        }
    }

    override suspend fun delete(checkListEntryId: Long) : DomainResponse<Unit> {
        return try {
            checkListEntryDao.deleteById(checkListEntryId)
            DomainResponse.Success(Unit)
        } catch (e: Exception) {
            DomainResponse.Error("Error deleting checklist entry: ${e.message}", code = 500)
        }
    }
}