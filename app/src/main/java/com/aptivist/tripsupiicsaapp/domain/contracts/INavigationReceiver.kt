package com.aptivist.tripsupiicsaapp.domain.contracts

import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction
import kotlinx.coroutines.flow.Flow

interface INavigationReceiver {
    val navigation: Flow<NavigationAction>
}