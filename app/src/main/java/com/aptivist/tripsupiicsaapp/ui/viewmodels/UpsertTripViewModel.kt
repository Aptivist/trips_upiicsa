package com.aptivist.tripsupiicsaapp.ui.viewmodels

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.ui.state.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpsertTripViewModel @Inject constructor(
    private val tripRepository: ITripRepository,
    private val navigationEmitter: INavigationEmitter
) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> get() = _name

    private val _destination = mutableStateOf("")
    val destination: State<String> get() = _destination

    private val _startDate = mutableStateOf("")
    val startDate: State<String> get() = _startDate

    private val _endDate = mutableStateOf("")
    val endDate: State<String> get() = _endDate

    private val _latitude = mutableStateOf("")
    val latitude: State<String> get() = _latitude

    private val _longitude = mutableStateOf("")
    val longitude: State<String> get() = _longitude

    private val _notes = mutableStateOf("")
    val notes: State<String> get() = _notes

    private val _photosUris = mutableStateSetOf<Uri?>()
    val photosUris: SnapshotStateSet<Uri?> get() = _photosUris

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private var tripId: Long? = null

    private val _dialogState = mutableStateOf<DialogState?>(null)
    val dialogState: State<DialogState?> get() = _dialogState

    fun onWriteName(name: String) {
        _name.value = name
    }

    fun onWriteDestination(destination: String) {
        _destination.value = destination
    }

    fun onSelectStartDate(startDate: String) {
        _startDate.value = startDate
    }

    fun onSelectEndDate(endDate: String) {
        _endDate.value = endDate
    }

    fun onWriteLatitude(latitude: String) {
        _latitude.value = latitude
    }

    fun onWriteLongitude(longitude: String) {
        _longitude.value = longitude
    }

    fun onWriteNotes(notes: String) {
        _notes.value = notes
    }

    fun onSelectCoverPhoto(uris: List<Uri>) {
        _photosUris.clear()
        _photosUris.addAll(uris)
    }

    fun onRemovePhoto(uri: Uri) {
        _photosUris.remove(uri)
    }

    fun onBack() {
        viewModelScope.launch {
            navigationEmitter.post(NavigationAction.NavigateBack)
        }
    }

    fun onDone() {
        _isLoading.value = true
//        viewModelScope.launch {
//            val trip = TripModel(
//                id = tripId,
//                name = _name.value,
//                destination = _destination.value,
//                startDate = _startDate.value,
//                endDate = _endDate.value,
//                location = LocationModel(
//                    latitude = _latitude.value.toDoubleOrNull() ?: 0.0,
//                    longitude = _longitude.value.toDoubleOrNull() ?: 0.0
//                ),
//                notes = _notes.value,
//                coverImageUrl = "",
//                photosUris = _photosUris.map { it.toString() }
//            )
//            val result = tripRepository.upsert(trip)
//
//            _isLoading.value = false
//
//            when (result) {
//                is DomainResponse.Success -> {
//                    tripId = result.data.id
//                    navigationEmitter.post(NavigationAction.NavigateBack)
//                }
//
//                is DomainResponse.Error -> {
//                    _dialogState.value = DialogState(
//                        titleResId = R.string.error,
//                        messageResId = R.string.there_was_an_error_trying_to_save_the_data_please_try_again,
//                        confirmText = R.string.ok,
//                        onConfirm = { onDismissDialog() },
//                        onDismiss = { onDismissDialog() }
//                    )
//                }
//            }
//        }
    }

    fun loadTrip(updateTripId: Long) {
//        viewModelScope.launch {
//            tripId = updateTripId
//            _isLoading.value = true
//            val result = tripRepository.getById(updateTripId)
//            when (result) {
//                is DomainResponse.Success -> {
//                    val trip = result.data
//                    _name.value = trip.name
//                    _destination.value = trip.destination
//                    _startDate.value = trip.startDate
//                    _endDate.value = trip.endDate
//                    _latitude.value = trip.location.latitude.toString()
//                    _longitude.value = trip.location.longitude.toString()
//                    _notes.value = trip.notes
//                    _photosUris.clear()
//                    _photosUris.addAll(trip.photosUris.map { it.toUri() })
//                }
//
//                is DomainResponse.Error -> {
//                    _dialogState.value = DialogState(
//                        titleResId = R.string.error,
//                        messageResId = R.string.there_was_an_error_loading_your_data_please_try_again,
//                        confirmText = R.string.ok,
//                        onConfirm = {
//                            onDismissDialog()
//                            onBack()
//                        },
//                        onDismiss = {
//                            onDismissDialog()
//                            onBack()
//                        }
//                    )
//                }
//            }
//            _isLoading.value = false
//        }
    }

    fun onDismissDialog() {
        _dialogState.value = null
    }

}