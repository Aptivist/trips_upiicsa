package com.aptivist.tripsupiicsaapp.ui.viewmodels

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction
import com.aptivist.tripsupiicsaapp.domain.contracts.IBackupService
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutes
import com.aptivist.tripsupiicsaapp.ui.state.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImportTripsViewModel @Inject constructor(
    private val backupService: IBackupService,
    private val navigationEmitter: INavigationEmitter
) : ViewModel() {

    private val _uri = mutableStateOf<String?>(null)

    val uri: State<String?>
        get() = _uri

    private val _dialogState = mutableStateOf<DialogState?>(null)
    val dialogState: State<DialogState?> get() = _dialogState

    fun setUri(uri: String) {
        _uri.value = uri
    }

    fun onImportTripsDialog() {
        _dialogState.value = DialogState(
            titleResId = R.string.alert,
            messageResId = R.string.the_imported_trips_will_replace_your_current_saved_trips_do_you_want_to_continue,
            confirmText = R.string.confirm,
            dismissText = R.string.cancel,
            onConfirm = {
                onDismissDialog()
                importTripsConfirmation()
            },
            onDismiss = { onDismissDialog() }
        )
    }

    fun importTripsConfirmation() {
        viewModelScope.launch {
            _uri.value?.let { uri ->
                val result = backupService.import(uri)
                when (result) {
                    is DomainResponse.Success -> {
                        _dialogState.value = DialogState(
                            titleResId = R.string.success,
                            messageResId = R.string.the_trips_have_been_imported_successfully,
                            confirmText = R.string.ok,
                            onConfirm = {
                                onDismissDialog()
                                onNavigateToHome()
                            },
                            onDismiss = {
                                onDismissDialog()
                                onNavigateToHome()
                            }
                        )
                    }

                    is DomainResponse.Error -> {
                        _dialogState.value = DialogState(
                            titleResId = R.string.error,
                            messageResId = R.string.there_was_an_error_trying_to_import_the_trips_please_try_again,
                            confirmText = R.string.ok,
                            onConfirm = { onDismissDialog() },
                            onDismiss = { onDismissDialog() }
                        )
                    }
                }
            }
        }
    }

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

    fun onShowDialog(
        @StringRes title: Int? = null,
        @StringRes message: Int? = null,
        @StringRes confirmText: Int = android.R.string.ok,
    ) {
        _dialogState.value = DialogState(
            titleResId = title,
            messageResId = message,
            confirmText = confirmText,
            onConfirm = { onDismissDialog() },
            onDismiss = { onDismissDialog() }
        )
    }

}