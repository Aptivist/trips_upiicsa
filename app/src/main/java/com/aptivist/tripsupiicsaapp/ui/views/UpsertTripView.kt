package com.aptivist.tripsupiicsaapp.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.aptivist.tripsupiicsaapp.ui.core.CustomOutlinedTextField
import com.aptivist.tripsupiicsaapp.ui.viewmodels.UpsertTripViewModel

@Composable
fun UpsertTripView(
    tripId: Long,
    viewModel: UpsertTripViewModel = hiltViewModel()
) {

    val name by remember { viewModel.name }

    LaunchedEffect(tripId) {
        if (tripId != -1L) {
            //LOAD TRIP
        }
    }

    UpsertTripContent(
        name = name,
        upsertTripViewActions = {
            when (it) {
                is UpsertTripViewActions.OnWriteName -> viewModel.onWriteName(it.name)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertTripContent(
    name: String,
    upsertTripViewActions: (UpsertTripViewActions) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = { Text("Insert Trip") },
                navigationIcon = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            CustomOutlinedTextField(
                value = name,
                label = "Name",
                onValueChange = { upsertTripViewActions.invoke(UpsertTripViewActions.OnWriteName(it)) },
            )
        }
    }
}

sealed class UpsertTripViewActions() {
    data class OnWriteName(val name: String) : UpsertTripViewActions()
}

@Preview
@Composable
fun UpsertTripPreview() {

}

