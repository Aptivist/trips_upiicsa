package com.aptivist.tripsupiicsaapp.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutes
import com.aptivist.tripsupiicsaapp.ui.state.DialogState
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImportTripsViewModel @Inject constructor(
    private val navigationEmitter: INavigationEmitter
) : ViewModel() {
    private val _uri = mutableStateOf<String?>(null)

    val uri: State<String?>
        get() = _uri
    private val _dialogState = mutableStateOf<DialogState?>(null)
    val dialogState: State<DialogState?> get() = _dialogState

    fun onBack() {
        viewModelScope.launch {
            navigationEmitter.post(NavigationAction.NavigateBack)
        }
    }

    fun onDismissDialog() {
        _dialogState.value = null
    }

    fun onNavigateToHome() {
        viewModelScope.launch {
            navigationEmitter.post(
                NavigationAction.NavigateTo(
                    route = AppRoutes.HOME,
                    clearBackStack = true
                )
            )
        }
    }
}