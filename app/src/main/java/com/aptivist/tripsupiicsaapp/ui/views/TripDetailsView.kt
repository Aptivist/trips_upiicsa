package com.aptivist.tripsupiicsaapp.ui.views

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.domain.models.CheckListEntryModel
import com.aptivist.tripsupiicsaapp.domain.models.JournalEntryModel
import com.aptivist.tripsupiicsaapp.domain.models.TripPhotoModel
import com.aptivist.tripsupiicsaapp.ui.core.CustomAlertDialog
import com.aptivist.tripsupiicsaapp.ui.core.CustomOutlinedTextField
import com.aptivist.tripsupiicsaapp.ui.core.JournalEntryCard
import com.aptivist.tripsupiicsaapp.ui.core.LoadingDialog
import com.aptivist.tripsupiicsaapp.ui.core.PhotosCarousel
import com.aptivist.tripsupiicsaapp.ui.core.TripCheckboxListTile
import com.aptivist.tripsupiicsaapp.ui.viewmodels.TripDetailsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsView(tripId: Long, viewModel: TripDetailsViewModel = hiltViewModel()) {
    //Lista de fotos recibidas de API
    val photos = remember { viewModel.photos }

    //Lista de Checkboxes recibidas de API
    val checkboxes = remember { viewModel.checkboxes }

    //Variable para getter del texto del Task
    val task by remember { viewModel.task }

    //Lista de entries en Journal recibidas de API
    val journalList = remember { viewModel.journalList }

    //SegmentedButton Options State Configuration
    //Pager State Configuration
    val optionsTabs = listOf(stringResource(R.string.checklist), stringResource(R.string.journal))

    //Maneja el estado ModalBottomSheet
    val showAddJournalEntrySheet by remember { viewModel.showAddJournalEntrySheet }

    //Variables para getters de los campos de Journal Entry
    val upsertJournalEntryModel by remember { viewModel.upsertJournalEntryModel }

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (tripId != -1L) {
            isLoading = true
            try {
                viewModel.setTrip(tripId)
                viewModel.loadPhotos()
                viewModel.loadCheckList()
                viewModel.loadJournalEntries()
            } finally {
                isLoading = false
            }
        }
    }

    TripDetailsViewContent(
        photos = photos,
        checkboxes = checkboxes,
        task = task,
        journalList = journalList,
        optionsTabs = optionsTabs,
        showAddJournalEntrySheet = showAddJournalEntrySheet,
        journalEntryModel = upsertJournalEntryModel
    ) {
        when (it) {
            is TripDetailsViewActions.NavigateBack -> viewModel.navigateBack()
            is TripDetailsViewActions.NavigateToUpsertTrip -> viewModel.navigateToUpsertTrip(
                tripId
            )

            is TripDetailsViewActions.RemoveTask -> viewModel.removeTask(it.task)
            is TripDetailsViewActions.AddTask -> viewModel.addTask(it.task)
            is TripDetailsViewActions.OnWriteTask -> viewModel.onWriteTask(it.task)
            is TripDetailsViewActions.ToggleJournalEntrySheet -> viewModel.toggleAddJournalEntrySheet(
                it.show, it.journalEntryModel
            )

            is TripDetailsViewActions.UpsertJournalEntry -> viewModel.upsertJournalEntry()

            is TripDetailsViewActions.RemoveJournalEntry -> viewModel.removeJournalEntry(it.entry)
            is TripDetailsViewActions.OnWriteJournalEntryTitle -> viewModel.onWriteJournalEntryTitle(
                it.title
            )

            is TripDetailsViewActions.OnWriteJournalEntryContent -> viewModel.onWriteJournalEntryContent(
                it.content
            )
        }
    }

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
private fun TripDetailsViewContent(
    photos: SnapshotStateList<TripPhotoModel>,
    checkboxes: SnapshotStateList<CheckListEntryModel>,
    task: String,
    journalList: SnapshotStateList<JournalEntryModel>,
    optionsTabs: List<String>,
    showAddJournalEntrySheet: Boolean,
    journalEntryModel: JournalEntryModel?,
    tripDetailsViewActions: (TripDetailsViewActions) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { optionsTabs.size })
    val sheetState = rememberModalBottomSheetState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.trip_details)) },
                navigationIcon = {
                    IconButton(
                        onClick = { tripDetailsViewActions.invoke(TripDetailsViewActions.NavigateBack) }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    FilledTonalIconButton(
                        onClick = { tripDetailsViewActions.invoke(TripDetailsViewActions.NavigateToUpsertTrip) }
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = null)
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            PhotosCarousel(photos = photos)
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                optionsTabs.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = optionsTabs.size
                        ),
                        icon = { },
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selected = pagerState.currentPage == index
                    ) {
                        Text(label)
                    }
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                snapPosition = SnapPosition.Start,
                verticalAlignment = Alignment.Top,
            ) { page ->
                when (page) {
                    0 -> LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize()
                    ) {
                        items(
                            checkboxes,
                            key = { it.id!! }
                        ) { checkboxItem ->
                            var checked by remember { mutableStateOf(checkboxItem.isChecked) }

                            TripCheckboxListTile(
                                modifier = Modifier.animateItem(),
                                isChecked = checked,
                                item = checkboxItem,
                                onCheckedChange = { checked = it },
                                onRemove = {
                                    tripDetailsViewActions.invoke(
                                        TripDetailsViewActions.RemoveTask(
                                            it
                                        )
                                    )
                                },
                            )
                        }
                        item {
                            CustomOutlinedTextField(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth(),
                                value = task,
                                label = stringResource(R.string.add_task),
                                onValueChange = {
                                    tripDetailsViewActions.invoke(
                                        TripDetailsViewActions.OnWriteTask(it)
                                    )
                                },
                                trailingIcon = {
                                    FilledIconButton(
                                        onClick = {
                                            tripDetailsViewActions.invoke(
                                                TripDetailsViewActions.AddTask(task)
                                            )
                                        },
                                    ) {
                                        Icon(Icons.Filled.Done, contentDescription = null)
                                    }
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        tripDetailsViewActions.invoke(
                                            TripDetailsViewActions.AddTask(
                                                task
                                            )
                                        )
                                    }
                                )
                            )
                        }
                    }

                    1 -> LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize()
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd,
                            ) {
                                Button(
                                    onClick = {
                                        tripDetailsViewActions.invoke(
                                            TripDetailsViewActions.ToggleJournalEntrySheet(
                                                true
                                            )
                                        )
                                    },
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically)
                                    {
                                        Icon(
                                            Icons.Filled.EditNote,
                                            contentDescription = null,
                                        )
                                        Text(stringResource(R.string.new_entry))
                                    }
                                }
                            }
                        }
                        items(
                            journalList,
                            key = { it.id!! }
                        ) { journalEntryItem ->
                            JournalEntryCard(
                                journalEntryItem = journalEntryItem,
                                onDeleteJournalItem = {
                                    tripDetailsViewActions.invoke(
                                        TripDetailsViewActions.RemoveJournalEntry(it)
                                    )
                                },
                                onUpdateJournalItem = {
                                    tripDetailsViewActions.invoke(
                                        TripDetailsViewActions.ToggleJournalEntrySheet(
                                            true,
                                            journalEntryItem
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        if (showAddJournalEntrySheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    tripDetailsViewActions.invoke(
                        TripDetailsViewActions.ToggleJournalEntrySheet(
                            false
                        )
                    )
                },
                sheetState = sheetState,
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    CustomOutlinedTextField(
                        value = journalEntryModel?.title ?: "",
                        label = stringResource(R.string.title),
                        onValueChange = {
                            tripDetailsViewActions.invoke(
                                TripDetailsViewActions.OnWriteJournalEntryTitle(
                                    it
                                )
                            )
                        },
                    )
                    Spacer(Modifier.height(8.dp))
                    CustomOutlinedTextField(
                        value = journalEntryModel?.content ?: "",
                        label = stringResource(R.string.content),
                        singleLine = false,
                        onValueChange = {
                            tripDetailsViewActions.invoke(
                                TripDetailsViewActions.OnWriteJournalEntryContent(
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
                            onClick = {
                                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        tripDetailsViewActions.invoke(
                                            TripDetailsViewActions.ToggleJournalEntrySheet(
                                                false
                                            )
                                        )
                                    }
                                }
                            },
                        ) {
                            Text(stringResource(R.string.cancel))
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                tripDetailsViewActions.invoke(
                                    TripDetailsViewActions.UpsertJournalEntry
                                )
                            },
                        ) {
                            Text(stringResource(R.string.done))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TripDetailsViewPreview() {
    TripDetailsViewContent(
        photos = remember { SnapshotStateList() },
        checkboxes = remember { SnapshotStateList() },
        task = "",
        journalList = remember { SnapshotStateList() },
        optionsTabs = listOf("Checklist", "Journal"),
        showAddJournalEntrySheet = false,
        journalEntryModel = JournalEntryModel(
            id = 1L,
            title = "Sample Journal Entry",
            content = "This is a sample journal entry content.",
        ),
        tripDetailsViewActions = {
            when (it) {
                is TripDetailsViewActions.NavigateBack -> {}
                is TripDetailsViewActions.NavigateToUpsertTrip -> {}
                is TripDetailsViewActions.RemoveTask -> {}
                is TripDetailsViewActions.AddTask -> {}
                is TripDetailsViewActions.OnWriteTask -> {}
                is TripDetailsViewActions.ToggleJournalEntrySheet -> {}
                TripDetailsViewActions.UpsertJournalEntry -> {}
                is TripDetailsViewActions.RemoveJournalEntry -> {}
                is TripDetailsViewActions.OnWriteJournalEntryTitle -> {}
                is TripDetailsViewActions.OnWriteJournalEntryContent -> {}
            }
        }
    )
}

sealed class TripDetailsViewActions {
    data object NavigateBack : TripDetailsViewActions()
    data object NavigateToUpsertTrip : TripDetailsViewActions()
    data class RemoveTask(val task: CheckListEntryModel) : TripDetailsViewActions()
    data class AddTask(val task: String) : TripDetailsViewActions()
    data class OnWriteTask(val task: String) : TripDetailsViewActions()
    data class ToggleJournalEntrySheet(
        val show: Boolean,
        val journalEntryModel: JournalEntryModel? = null
    ) : TripDetailsViewActions()

    data object UpsertJournalEntry : TripDetailsViewActions()
    data class RemoveJournalEntry(val entry: JournalEntryModel) : TripDetailsViewActions()
    data class OnWriteJournalEntryTitle(val title: String) : TripDetailsViewActions()
    data class OnWriteJournalEntryContent(val content: String) : TripDetailsViewActions()
}