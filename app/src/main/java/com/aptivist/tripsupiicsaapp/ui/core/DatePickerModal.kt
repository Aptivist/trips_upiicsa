package com.aptivist.tripsupiicsaapp.ui.core

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aptivist.tripsupiicsaapp.ui.utils.fixUtcToLocalMillis
import com.aptivist.tripsupiicsaapp.ui.utils.getTodaySystemDateInMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    selectedDateMillis: Long?,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis ?: getTodaySystemDateInMillis()
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(fixUtcToLocalMillis(datePickerState.selectedDateMillis))
                    onDismiss()
                }
            ) {
                Text("Done")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}