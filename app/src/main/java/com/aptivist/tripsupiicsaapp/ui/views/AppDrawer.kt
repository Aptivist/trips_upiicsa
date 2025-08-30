package com.aptivist.tripsupiicsaapp.ui.views

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.lifecycleScope
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.ui.core.CustomAlertDialog
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutes
import com.aptivist.tripsupiicsaapp.ui.viewmodels.AppDrawerViewModel
import com.aptivist.tripsupiicsaapp.ui.viewmodels.AppDrawerViewModelAction
import kotlinx.coroutines.launch
import kotlin.invoke

@Composable
fun AppDrawer(viewModel: AppDrawerViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val shareTripContract =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.CreateDocument("application/octet-stream")) { uri ->
            uri?.let {
                try {
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        val backup = viewModel.exportData.value
                        backup?.let { file ->
                            file.inputStream().use { inputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }
                    }
                    viewModel.onShowDialog(
                        title = R.string.success,
                        message = R.string.the_trips_have_been_exported_successfully,
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    viewModel.onShowDialog(
                        title = R.string.error,
                        message = R.string.something_went_wrong_please_try_again,
                    )
                }

            } ?: run {
                viewModel.onShowDialog(
                    title = R.string.canceled,
                    message = R.string.the_export_of_trips_has_been_cancelled,
                )
            }
        }

    LifecycleResumeEffect(Unit) {
        val job = lifecycleScope.launch {
            viewModel.viewModelActions.collect { vmAction ->
                when (vmAction) {
                    is AppDrawerViewModelAction.ExportBackup -> {
                        shareTripContract.launch("backup_${System.currentTimeMillis()}.trip")
                    }

                    AppDrawerViewModelAction.ShareBackup -> {
                        val backupFile = viewModel.exportData.value
                        backupFile?.let { file ->
                            val uri = FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.provider",
                                file
                            )
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "application/octet-stream"
                                putExtra(Intent.EXTRA_STREAM, uri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Backup"))
                        }
                    }
                }
            }
        }

        onPauseOrDispose {
            job.cancel()
        }
    }

    AppDrawerContent{
        when (it) {
            is AppDrawerActions.ExportTrips -> viewModel.backupData(AppDrawerViewModelAction.ExportBackup)
            is AppDrawerActions.ImportTrips -> viewModel.restoreData()
            is AppDrawerActions.Logout -> {
                //TODO: Implement logout functionality
            }
            is AppDrawerActions.ShareTrips -> viewModel.backupData(AppDrawerViewModelAction.ShareBackup)
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

@Composable
fun AppDrawerContent(
    appDrawerActions: (AppDrawerActions) -> Unit
) {
    ModalDrawerSheet {
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.export_trips)) },
            selected = false,
            icon = { Icon(Icons.Filled.ArrowCircleUp, contentDescription = null) },
            onClick = { appDrawerActions.invoke(AppDrawerActions.ExportTrips) }
        )
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.share_trips)) },
            selected = false,
            icon = { Icon(Icons.Filled.Share, contentDescription = "") },
            onClick = {appDrawerActions.invoke(AppDrawerActions.ShareTrips) }
        )
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.import_trips)) },
            selected = false,
            icon = { Icon(Icons.Filled.ArrowCircleDown, contentDescription = "") },
            onClick = { appDrawerActions.invoke(AppDrawerActions.ImportTrips) }
        )
        HorizontalDivider()
        Spacer(Modifier.weight(1f))
        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 24.dp),
            onClick = { appDrawerActions.invoke(AppDrawerActions.Logout) },
        ) {
            Text(stringResource(R.string.logout))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "")
        }
    }
}

@Preview
@Composable
fun AppDrawerPreview() {
    AppDrawerContent() {
        // No-op
    }
}

sealed class AppDrawerActions {
    data object ExportTrips : AppDrawerActions()
    data object ImportTrips : AppDrawerActions()
    data object ShareTrips : AppDrawerActions()
    data object Logout : AppDrawerActions()
}