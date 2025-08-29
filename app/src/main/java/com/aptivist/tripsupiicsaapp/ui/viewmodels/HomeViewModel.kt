package com.aptivist.tripsupiicsaapp.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction
import com.aptivist.tripsupiicsaapp.data.local.repository.SuccessMockDataTripRepository
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutesArgs
import com.aptivist.tripsupiicsaapp.ui.state.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val tripRepository: SuccessMockDataTripRepository,
    private val navigationEmitter: INavigationEmitter
) : ViewModel() {

    private val _trips = mutableStateListOf<TripModel>()
    val trips: List<TripModel> get() = _trips

    private val _dialogState = mutableStateOf<DialogState?>(null)
    val dialogState: State<DialogState?> get() = _dialogState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    init {


        viewModelScope.launch {
            val response = tripRepository.getAll()

            when (response) {
                is DomainResponse.Success -> {
                    _trips.clear()
                    _trips.addAll(response.data)
                }

                is DomainResponse.Error -> {
                    println("Error: ${response.message}")
                }
            }
        }
    }

    fun loadTrips() {
        _isLoading.value = true
        viewModelScope.launch {
            val response = tripRepository.getAll()
            _isLoading.value = false
            when (response) {
                is DomainResponse.Success -> {
                    _trips.clear()
                    //_trips.addAll(response.data.sortedBy { it.startDate })
                }

                is DomainResponse.Error -> {
                    _dialogState.value = DialogState(
                        titleResId = R.string.error,
                        messageResId = R.string.there_was_an_error_loading_your_data_please_try_again,
                        confirmText = R.string.retry,
                        dismissText = R.string.cancel,
                        onConfirm = {
                            onDismissDialog()
                            loadTrips()
                        },
                        onDismiss = { onDismissDialog() }
                    )
                }
            }
        }
    }

    fun navigateTo(route: String, tripId: Long? = null) {
        viewModelScope.launch {
            navigationEmitter.post(
                NavigationAction.NavigateToWithArgs(
                    route = route,
                    args = tripId?.let { mapOf(AppRoutesArgs.TRIP_ID to it.toString()) }
                        ?: emptyMap()
                )
            )
        }
    }

    fun removeTrip(id: Long) {
//        viewModelScope.launch {
//            val tripToRemove = _trips.find { it.id == id }
//            if (tripToRemove != null) {
//                val response = tripRepository.delete(id)
//                when (response) {
//                    is DomainResponse.Success -> {
//                        _trips.removeIf { it.id == id }
//                    }
//
//                    is DomainResponse.Error -> {
//                        _dialogState.value = DialogState(
//                            titleResId = R.string.error,
//                            messageResId = R.string.there_was_an_error_trying_to_delete_the_element_please_try_again,
//                            confirmText = R.string.ok,
//                            onConfirm = { onDismissDialog() },
//                            onDismiss = { onDismissDialog() }
//                        )
//                    }
//                }
//            }
//        }
    }

    fun onDismissDialog() {
        _dialogState.value = null
    }

}