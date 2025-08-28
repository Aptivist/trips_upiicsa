package com.aptivist.tripsupiicsaapp.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UpsertTripViewModel @Inject constructor() : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> get() = _name

    fun onWriteName(name: String) {
        _name.value = name
    }

}