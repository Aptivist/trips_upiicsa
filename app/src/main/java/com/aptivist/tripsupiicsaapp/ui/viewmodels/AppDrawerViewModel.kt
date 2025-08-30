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
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutesArgs
import com.aptivist.tripsupiicsaapp.ui.state.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AppDrawerViewModel @Inject constructor(
    private val backupService: IBackupService,
    private val navigationEmitter: INavigationEmitter,
) : ViewModel() {

    private val _viewModelActions = MutableSharedFlow<AppDrawerViewModelAction>()
    val viewModelActions = _viewModelActions.asSharedFlow()

    private val _exportData = mutableStateOf<File?>(null)
    val exportData: State<File?> get() = _exportData

    private val _dialogState = mutableStateOf<DialogState?>(null)
    val dialogState: State<DialogState?> get() = _dialogState

    fun backupData(action: AppDrawerViewModelAction) {
        viewModelScope.launch {
            when (val result = backupService.export()) {
                is DomainResponse.Error -> {
                    _dialogState.value = DialogState(
                        titleResId = R.string.error,
                        messageResId = R.string.something_went_wrong_please_try_again,
                        confirmText = R.string.ok,
                        onConfirm = { onDismissDialog() },
                        onDismiss = { onDismissDialog() }
                    )
                }

                is DomainResponse.Success -> {
                    _exportData.value = result.data
                    _viewModelActions.emit(action)
                }
            }
        }
    }

    fun restoreData() {
        viewModelScope.launch {
            navigationEmitter.post(
                NavigationAction.NavigateToWithArgs(
                    AppRoutes.IMPORT_TRIPS,
                    mapOf(AppRoutesArgs.IMPORT_TRIPS_FILE to "")
                )
            )
        }
    }

    fun onDismissDialog() {
        _dialogState.value = null
    }

    fun onShowDialog(
        @StringRes title: Int? = null,
        @StringRes message: Int? = null,
        @StringRes confirmText: Int = R.string.ok,
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

sealed class AppDrawerViewModelAction {
    data object ExportBackup : AppDrawerViewModelAction()
    data object ShareBackup : AppDrawerViewModelAction()
}