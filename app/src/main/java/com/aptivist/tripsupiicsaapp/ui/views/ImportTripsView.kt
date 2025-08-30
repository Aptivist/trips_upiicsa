package com.aptivist.tripsupiicsaapp.ui.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileOpen
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.ui.core.CustomAlertDialog
import com.aptivist.tripsupiicsaapp.ui.viewmodels.ImportTripsViewModel
import kotlin.invoke

@Composable
fun ImportTripsView(incomingUri: String?, viewModel: ImportTripsViewModel = hiltViewModel()) {

    val uri by remember { viewModel.uri }


    ImportTripsViewContent(uri) {
        when (it) {
            ImportTripsViewAction.ImportTrips -> {}
            is ImportTripsViewAction.SetUri -> {}
            ImportTripsViewAction.OnBack -> viewModel.onBack()
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

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImportTripsViewContent(
    uri: String? = null,
    onImportTripsViewAction: (ImportTripsViewAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.import_trips)) },
                navigationIcon = {
                    IconButton(
                        onClick = { onImportTripsViewAction.invoke(ImportTripsViewAction.OnBack) }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            OutlinedCard(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(Icons.Filled.FileDownload, contentDescription = null)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        stringResource(R.string.choose_a_file_to_import),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Spacer(Modifier.height(18.dp))
                    uri?.let {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(12.dp),
                                    clip = false,
                                )
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.InsertDriveFile,
                                contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(uri)
                        }
                        Spacer(Modifier.height(18.dp))
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {  }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.FileOpen, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text(text = uri?.let {
                                stringResource(R.string.change_file)
                            } ?: stringResource(R.string.select_file)
                            )
                        }
                    }

                    FilledTonalButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { },
                        enabled = uri != null
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.ArrowCircleDown, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.import_trips))
                        }
                    }
                }
            }
        }
    }

}

sealed class ImportTripsViewAction {
    data object SetUri : ImportTripsViewAction()
    data object ImportTrips : ImportTripsViewAction()
    object OnBack : ImportTripsViewAction()
}

@Preview(showSystemUi = true)
@Composable
private fun ImportTripsViewPreview() {
    ImportTripsViewContent(
        uri = "This is the uri",
        onImportTripsViewAction = {}
    )
}