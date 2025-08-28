package com.aptivist.tripsupiicsaapp.domain.contracts

import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction

interface INavigationEmitter {
    suspend fun post(action: NavigationAction)
}