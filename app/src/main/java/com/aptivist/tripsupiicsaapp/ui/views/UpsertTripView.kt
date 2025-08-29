package com.aptivist.tripsupiicsaapp.ui.views

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.ui.core.CustomAlertDialog
import com.aptivist.tripsupiicsaapp.ui.core.CustomOutlinedTextField
import com.aptivist.tripsupiicsaapp.ui.core.DatePickerField
import com.aptivist.tripsupiicsaapp.ui.core.LoadingDialog
import com.aptivist.tripsupiicsaapp.ui.core.TripPhotoRow
import com.aptivist.tripsupiicsaapp.ui.viewmodels.UpsertTripViewModel

@Composable
fun UpsertTripView(
    tripId: Long,
    viewModel: UpsertTripViewModel = hiltViewModel()
) {

    val name by remember { viewModel.name }
    val destination by remember { viewModel.destination }
    val startDate by remember { viewModel.startDate }
    val endDate by remember { viewModel.endDate }
    val latitude by remember { viewModel.latitude }
    val longitude by remember { viewModel.longitude }
    val notes by remember { viewModel.notes }
    val photosUris = remember { viewModel.photosUris }
    val isLoading by remember { viewModel.isLoading }

    LaunchedEffect(tripId) {
        if (tripId != -1L) {
            viewModel.loadTrip(tripId)
        }
    }

    UpsertTripContent(
        tripId = tripId,
        name = name,
        destination = destination,
        startDate = startDate,
        endDate = endDate,
        latitude = latitude,
        longitude = longitude,
        notes = notes,
        photosUris = photosUris,
        upsertTripViewActions = {
            when (it) {
                is UpsertTripViewActions.OnBack -> viewModel.onBack()
                is UpsertTripViewActions.OnWriteName -> viewModel.onWriteName(it.name)
                is UpsertTripViewActions.OnWriteDestination -> viewModel.onWriteDestination(it.destination)
                is UpsertTripViewActions.OnSelectStartDate -> viewModel.onSelectStartDate(it.startDate)
                is UpsertTripViewActions.OnSelectEndDate -> viewModel.onSelectEndDate(it.endDate)
                is UpsertTripViewActions.OnWriteLatitude -> viewModel.onWriteLatitude(it.latitude)
                is UpsertTripViewActions.OnWriteLongitude -> viewModel.onWriteLongitude(it.longitude)
                is UpsertTripViewActions.OnWriteNotes -> viewModel.onWriteNotes(it.notes)
                is UpsertTripViewActions.OnSelectCoverPhoto -> viewModel.onSelectCoverPhoto(it.uris)
                is UpsertTripViewActions.OnRemovePhoto -> viewModel.onRemovePhoto(it.uri)
                UpsertTripViewActions.OnSave -> viewModel.onDone()
            }
        }
    )

    viewModel.dialogState.value?.let { state ->
        CustomAlertDialog(
            confirmText = stringResource(state.confirmText),
            dismissText = state.dismissText?.let { stringResource(it) },
            title = state.titleResId?.let { stringResource(it) },
            text = state.messageResId?.let { stringResource(it) },
            onDismiss = { state.onDismiss.invoke() },
            onConfirm = { state.onConfirm.invoke() },
        )
    }

    if (isLoading) {
        LoadingDialog()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertTripContent(
    tripId: Long,
    name: String,
    destination: String,
    startDate: String,
    endDate: String,
    latitude: String,
    longitude: String,
    notes: String,
    photosUris: Set<Uri?>,
    upsertTripViewActions: (UpsertTripViewActions) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = { Text(stringResource(if (tripId == -1L) R.string.create_a_trip else R.string.update_trip)) },
                navigationIcon = {
                    IconButton(
                        onClick = { upsertTripViewActions.invoke(UpsertTripViewActions.OnBack) }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            CustomOutlinedTextField(
                value = name,
                label = stringResource(R.string.name),
                onValueChange = { upsertTripViewActions.invoke(UpsertTripViewActions.OnWriteName(it)) },
            )
            Spacer(Modifier.height(8.dp))
            CustomOutlinedTextField(
                value = destination,
                label = stringResource(R.string.destination),
                onValueChange = {
                    upsertTripViewActions.invoke(
                        UpsertTripViewActions.OnWriteDestination(
                            it
                        )
                    )
                },
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                DatePickerField(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.start_date),
                    value = startDate,
                    onDateSelected = {
                        upsertTripViewActions.invoke(
                            UpsertTripViewActions.OnSelectStartDate(
                                it
                            )
                        )
                    }
                )
                Spacer(Modifier.width(8.dp))
                DatePickerField(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.end_date),
                    value = endDate,
                    onDateSelected = {
                        upsertTripViewActions.invoke(
                            UpsertTripViewActions.OnSelectEndDate(
                                it
                            )
                        )
                    }
                )
            }
            Spacer(Modifier.height(12.dp))
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(12.dp),
                ) {
                    Button(
                        onClick = { }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                Icons.Filled.Photo,
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(R.string.select_photos))
                        }
                    }
                    photosUris.forEach { uri ->
                        TripPhotoRow(
                            uri,
                            onRemovePhoto = {
                                upsertTripViewActions.invoke(
                                    UpsertTripViewActions.OnRemovePhoto(
                                        it
                                    )
                                )
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomOutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = latitude,
                    label = stringResource(R.string.latitude),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        upsertTripViewActions.invoke(
                            UpsertTripViewActions.OnWriteLatitude(
                                it
                            )
                        )
                    },
                )
                Spacer(Modifier.width(8.dp))
                CustomOutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = longitude,
                    label = stringResource(R.string.longitude),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        upsertTripViewActions.invoke(
                            UpsertTripViewActions.OnWriteLongitude(
                                it
                            )
                        )
                    },
                )
            }
            Spacer(Modifier.height(8.dp))
            CustomOutlinedTextField(
                value = notes,
                label = stringResource(R.string.notes),
                singleLine = false,
                onValueChange = {
                    upsertTripViewActions.invoke(
                        UpsertTripViewActions.OnWriteNotes(
                            it
                        )
                    )
                },
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                FilledTonalButton(
                    modifier = Modifier.weight(1f),
                    onClick = { upsertTripViewActions.invoke(UpsertTripViewActions.OnBack) },
                ) {
                    Text(stringResource(R.string.cancel))
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        upsertTripViewActions.invoke(UpsertTripViewActions.OnSave)
                    },
                ) {
                    Text(stringResource(R.string.done))
                }
            }
        }
    }
}

sealed class UpsertTripViewActions() {
    object OnBack : UpsertTripViewActions()
    data class OnWriteName(val name: String) : UpsertTripViewActions()
    data class OnWriteDestination(val destination: String) : UpsertTripViewActions()
    data class OnSelectStartDate(val startDate: String) : UpsertTripViewActions()
    data class OnSelectEndDate(val endDate: String) : UpsertTripViewActions()
    data class OnWriteLatitude(val latitude: String) : UpsertTripViewActions()
    data class OnWriteLongitude(val longitude: String) : UpsertTripViewActions()
    data class OnWriteNotes(val notes: String) : UpsertTripViewActions()
    data class OnSelectCoverPhoto(val uris: List<Uri>) : UpsertTripViewActions()
    data class OnRemovePhoto(val uri: Uri) : UpsertTripViewActions()
    object OnSave : UpsertTripViewActions()
}

@Preview(showSystemUi = true)
@Composable
private fun UpsertTripViewPreview() {
    UpsertTripContent(
        tripId = -1,
        name = "Trip to Paris",
        destination = "Paris, France",
        startDate = "2023-10-01",
        endDate = "2023-10-10",
        latitude = "48.8566",
        longitude = "2.3522",
        notes = "Excited for the Eiffel Tower!",
        photosUris = setOf()
    ) {
        when (it) {
            UpsertTripViewActions.OnBack -> {}
            is UpsertTripViewActions.OnRemovePhoto -> {}
            is UpsertTripViewActions.OnSelectCoverPhoto -> {}
            is UpsertTripViewActions.OnSelectEndDate -> {}
            is UpsertTripViewActions.OnSelectStartDate -> {}
            is UpsertTripViewActions.OnWriteDestination -> {}
            is UpsertTripViewActions.OnWriteLatitude -> {}
            is UpsertTripViewActions.OnWriteLongitude -> {}
            is UpsertTripViewActions.OnWriteName -> {}
            is UpsertTripViewActions.OnWriteNotes -> {}
            UpsertTripViewActions.OnSave -> {}
        }
    }
}

