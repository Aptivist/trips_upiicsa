package com.aptivist.tripsupiicsaapp.di

import com.aptivist.tripsupiicsaapp.data.local.backup.DatabaseBackupServiceImplementation
import com.aptivist.tripsupiicsaapp.domain.contracts.IBackupService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BackupModule {

    @Singleton
    @Binds
    abstract fun bindBackupService(backupServiceImpl: DatabaseBackupServiceImplementation): IBackupService
}