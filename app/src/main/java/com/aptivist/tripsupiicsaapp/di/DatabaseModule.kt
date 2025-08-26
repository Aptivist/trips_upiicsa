package com.aptivist.tripsupiicsaapp.di

import android.content.Context
import androidx.room.Room
import com.aptivist.tripsupiicsaapp.data.local.database.TripsDatabase
import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : TripsDatabase {
        return Room.databaseBuilder(
            context,
            TripsDatabase::class.java,
            "trips_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTripDao(database : TripsDatabase) : TripDao {
        return database.tripDao()
    }

}