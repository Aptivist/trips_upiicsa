package com.aptivist.tripsupiicsaapp.di

import com.aptivist.tripsupiicsaapp.data.local.repository.CheckListEntryRepository
import com.aptivist.tripsupiicsaapp.data.local.repository.DatabaseTripRepository
import com.aptivist.tripsupiicsaapp.data.local.repository.JournalEntryRepository
import com.aptivist.tripsupiicsaapp.data.local.repository.SuccessMockDataTripRepository
import com.aptivist.tripsupiicsaapp.domain.contracts.ICheckListEntryRepository
import com.aptivist.tripsupiicsaapp.domain.contracts.IJournalEntryRepository
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTripRepository(implementation: DatabaseTripRepository): ITripRepository

    /*
    companion object {

        @Provides
        @Singleton
        fun provideTripRepository(tripDao: TripDao) : ITripRepository {
            return DatabaseTripRepository(tripDao)
        }

    }*/

    @Binds
    @Singleton
    abstract fun bindJournalEntryRepository(
        implementation: JournalEntryRepository
    ): IJournalEntryRepository

    @Binds
    @Singleton
    abstract fun bindCheckListEntryRepository(
        implementation: CheckListEntryRepository
    ): ICheckListEntryRepository

}