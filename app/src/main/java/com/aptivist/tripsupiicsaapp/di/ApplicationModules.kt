package com.aptivist.tripsupiicsaapp.di

import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationBus
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModules {

    val navigationBus = NavigationBus()

    @Provides
    @Singleton
    fun provideNavigationEmitter(): INavigationEmitter = navigationBus

    @Provides
    @Singleton
    fun provideNavigationReceiver(): INavigationReceiver = navigationBus

}