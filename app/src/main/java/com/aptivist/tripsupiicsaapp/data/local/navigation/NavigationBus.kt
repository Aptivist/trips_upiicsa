package com.aptivist.tripsupiicsaapp.data.local.navigation

import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationReceiver
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class NavigationBus @Inject constructor() : INavigationEmitter, INavigationReceiver {

    private val _navigation = MutableSharedFlow<NavigationAction>()
    override val navigation = _navigation.asSharedFlow()

    override suspend fun post(action: NavigationAction) {
        _navigation.emit(action)
    }

}


sealed class NavigationAction {

    data class NavigateTo(val route: String, val clearBackStack: Boolean = false) :
        NavigationAction()

    data class NavigateToWithArgs(
        val route: String,
        val args: Map<String, String>,
        val clearBackStack: Boolean = false,
    ) : NavigationAction()

    data object NavigateBack : NavigationAction()

}