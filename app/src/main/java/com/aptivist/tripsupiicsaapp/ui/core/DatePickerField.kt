package com.aptivist.tripsupiicsaapp.ui.core

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.aptivist.tripsupiicsaapp.ui.utils.convertMillisToDate

@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onDateSelected: (String) -> Unit
) {
    var showModal by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        modifier = modifier
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) showModal = true
                }

            },
        readOnly = true,
        shape = RoundedCornerShape(24.dp),
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = label
            )
        }
    )

    if (showModal) {
        DatePickerModal(
            selectedDateMillis = selectedDate,
            onDateSelected = { millis ->
                selectedDate = millis
                onDateSelected(convertMillisToDate(millis))
            },
            onDismiss = {
                showModal = false
            }
        )
    }
}