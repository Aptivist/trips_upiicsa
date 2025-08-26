package com.aptivist.tripsupiicsaapp.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.tripsupiicsaapp.data.local.repository.DatabaseTripRepository
import com.aptivist.tripsupiicsaapp.data.local.repository.SuccessMockDataTripRepository
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.LocationModel
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val tripRepository: SuccessMockDataTripRepository
) : ViewModel() {

    private val _trips = mutableStateListOf<TripModel>()
    val trips: List<TripModel> get() = _trips

    init {


        viewModelScope.launch {
            val response = tripRepository.getAll()

            when(response) {
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

}