package com.aptivist.tripsupiicsaapp.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutesArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val tripRepository: ITripRepository,
    private val navigationEmitter: INavigationEmitter
) : ViewModel() {

    private val _trips = mutableStateListOf<TripModel>()
    val trips: List<TripModel> get() = _trips

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
        viewModelScope.launch { tripRepository.getAll() }
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

}