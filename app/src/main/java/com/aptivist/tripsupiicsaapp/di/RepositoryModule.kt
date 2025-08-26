package com.aptivist.tripsupiicsaapp.di

import androidx.lifecycle.ViewModel
import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripDao
import com.aptivist.tripsupiicsaapp.data.local.repository.DatabaseTripRepository
import com.aptivist.tripsupiicsaapp.data.local.repository.SuccessMockDataTripRepository
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
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

}