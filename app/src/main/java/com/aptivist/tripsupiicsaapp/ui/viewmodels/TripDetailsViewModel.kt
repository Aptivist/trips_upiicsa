package com.aptivist.tripsupiicsaapp.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction
import com.aptivist.tripsupiicsaapp.domain.contracts.ICheckListEntryRepository
import com.aptivist.tripsupiicsaapp.domain.contracts.IJournalEntryRepository
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.CheckListEntryModel
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.JournalEntryModel
import com.aptivist.tripsupiicsaapp.domain.models.TripPhotoModel
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutes
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutesArgs
import com.aptivist.tripsupiicsaapp.ui.state.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    private val tripRepository: ITripRepository,
    private val checkListEntryRepository: ICheckListEntryRepository,
    private val journalEntryRepository: IJournalEntryRepository,
    private val navigationEmitter: INavigationEmitter
) : ViewModel() {

    private val _photos = mutableStateListOf<TripPhotoModel>()
    val photos: SnapshotStateList<TripPhotoModel> get() = _photos

    private val _checkboxes = mutableStateListOf<CheckListEntryModel>()
    val checkboxes: SnapshotStateList<CheckListEntryModel> get() = _checkboxes

    private val _task = mutableStateOf("")
    val task: State<String> get() = _task

    private val _journalList = mutableStateListOf<JournalEntryModel>()
    val journalList: SnapshotStateList<JournalEntryModel> get() = _journalList


    private val _upsertJournalEntryModel = mutableStateOf<JournalEntryModel?>(null)
    val upsertJournalEntryModel: State<JournalEntryModel?> get() = _upsertJournalEntryModel


    private val _showAddJournalEntrySheet = mutableStateOf(false)
    val showAddJournalEntrySheet: State<Boolean> get() = _showAddJournalEntrySheet

    private var tripId: Long = -1L // This should be set when loading a trip

    private val _dialogState = mutableStateOf<DialogState?>(null)
    val dialogState: State<DialogState?> get() = _dialogState

    fun addTask(task: String) {
        if (task.isNotEmpty()) {
            viewModelScope.launch {
                val newTask = CheckListEntryModel(
                    name = task,
                    isChecked = false
                )
                val response = checkListEntryRepository.upsert(tripId, newTask)
                when (response) {
                    is DomainResponse.Success -> {
                        _checkboxes.add(response.data)
                        _task.value = ""
                    }

                    is DomainResponse.Error -> {
                        _dialogState.value = DialogState(
                            titleResId = R.string.error,
                            messageResId = R.string.something_went_wrong_please_try_again,
                            confirmText = R.string.ok,
                            onConfirm = { onDismissDialog() },
                            onDismiss = { onDismissDialog() }
                        )
                    }
                }
            }
        }
    }

    fun removeTask(item: CheckListEntryModel) {
        viewModelScope.launch {

            item.id ?: return@launch

            val response = checkListEntryRepository.delete(item.id)
            when (response) {
                is DomainResponse.Success -> {
                    _checkboxes.remove(item)
                }

                is DomainResponse.Error -> {
                    _dialogState.value = DialogState(
                        titleResId = R.string.error,
                        messageResId = R.string.there_was_an_error_trying_to_delete_the_element_please_try_again,
                        confirmText = R.string.ok,
                        onConfirm = { onDismissDialog() },
                        onDismiss = { onDismissDialog() }
                    )
                }
            }
        }
    }

    fun onWriteTask(text: String) {
        _task.value = text
    }

    fun upsertJournalEntry() {
        if (_upsertJournalEntryModel.value == null || _upsertJournalEntryModel.value?.title == "") return
        viewModelScope.launch {
            _upsertJournalEntryModel.value?.let { item ->
                val updatedEntry = item.copy(
                    id = item.id,
                    title = item.title,
                    content = item.content
                )
                val response = journalEntryRepository.upsert(tripId, updatedEntry)
                when (response) {
                    is DomainResponse.Success -> {
                        val index = _journalList.indexOfFirst { it.id == item.id }
                        if (index != -1) {
                            _journalList[index] = response.data
                        } else {
                            _journalList.add(response.data)
                        }
                        toggleAddJournalEntrySheet(false)
                    }

                    is DomainResponse.Error -> {
                        _dialogState.value = DialogState(
                            titleResId = R.string.error,
                            messageResId = R.string.something_went_wrong_please_try_again,
                            confirmText = R.string.ok,
                            onConfirm = { onDismissDialog() },
                            onDismiss = { onDismissDialog() }
                        )
                    }
                }
            }
        }
    }

    fun onWriteJournalEntryTitle(title: String) {
        _upsertJournalEntryModel.value = _upsertJournalEntryModel.value?.copy(title = title)
    }

    fun onWriteJournalEntryContent(content: String) {
        _upsertJournalEntryModel.value = _upsertJournalEntryModel.value?.copy(content = content)
    }

    fun removeJournalEntry(item: JournalEntryModel) {
        viewModelScope.launch {
            item.id ?: return@launch // Ensure the item has an ID

            val response = journalEntryRepository.delete(item.id)
            when (response) {
                is DomainResponse.Success -> {
                    _journalList.remove(item)
                }

                is DomainResponse.Error -> {
                    _dialogState.value = DialogState(
                        titleResId = R.string.error,
                        messageResId = R.string.there_was_an_error_trying_to_delete_the_element_please_try_again,
                        confirmText = R.string.ok,
                        onConfirm = { onDismissDialog() },
                        onDismiss = { onDismissDialog() }
                    )
                }
            }
        }
    }

    fun navigateToUpsertTrip(tripId: Long) {
        viewModelScope.launch {
            navigationEmitter.post(
                NavigationAction.NavigateToWithArgs(
                    route = AppRoutes.UPSERT_TRIP,
                    args = mapOf(AppRoutesArgs.TRIP_ID to tripId.toString())
                )
            )
        }
    }

    fun toggleAddJournalEntrySheet(show: Boolean, entry: JournalEntryModel? = null) {
        if (show) {
            if (entry != null) {
                _upsertJournalEntryModel.value = entry
            } else {
                _upsertJournalEntryModel.value = JournalEntryModel(
                    id = null,
                    title = "",
                    content = ""
                )
            }
        } else {
            _upsertJournalEntryModel.value = null
        }
        _showAddJournalEntrySheet.value = show
    }

    fun navigateBack() {
        viewModelScope.launch {
            navigationEmitter.post(NavigationAction.NavigateBack)
        }
    }

    fun setTrip(updateTripId: Long) {
        tripId = updateTripId
    }

    fun loadPhotos() {
//        viewModelScope.launch {
//            val response = tripRepository.getTripPhotos(tripId)
//            when (response) {
//                is DomainResponse.Success -> {
//                    _photos.clear()
//                    _photos.addAll(response.data)
//                }
//
//                is DomainResponse.Error -> {
//                    _dialogState.value = DialogState(
//                        titleResId = R.string.error,
//                        messageResId = R.string.there_was_an_error_trying_to_load_the_photos_please_try_again,
//                        confirmText = R.string.ok,
//                        onConfirm = { onDismissDialog() },
//                        onDismiss = { onDismissDialog() }
//                    )
//                }
//            }
//        }
    }

    fun loadCheckList() {
        viewModelScope.launch {
            val response = checkListEntryRepository.getAllByTripId(tripId)
            when (response) {
                is DomainResponse.Success -> {
                    _checkboxes.clear()
                    _checkboxes.addAll(response.data)
                }

                is DomainResponse.Error -> {
                    _dialogState.value = DialogState(
                        titleResId = R.string.error,
                        messageResId = R.string.there_was_an_error_loading_your_data_please_try_again,
                        confirmText = R.string.ok,
                        onConfirm = { onDismissDialog() },
                        onDismiss = { onDismissDialog() }
                    )
                }
            }
        }
    }

    fun loadJournalEntries() {
        viewModelScope.launch {
            val response = journalEntryRepository.getByTripId(tripId)
            when (response) {
                is DomainResponse.Success -> {
                    _journalList.clear()
                    _journalList.addAll(response.data)
                }

                is DomainResponse.Error -> {
                    _dialogState.value = DialogState(
                        titleResId = R.string.error,
                        messageResId = R.string.there_was_an_error_loading_your_data_please_try_again,
                        confirmText = R.string.ok,
                        onConfirm = { onDismissDialog() },
                        onDismiss = { onDismissDialog() }
                    )
                }
            }
        }
    }


    fun onDismissDialog() {
        _dialogState.value = null
    }

}