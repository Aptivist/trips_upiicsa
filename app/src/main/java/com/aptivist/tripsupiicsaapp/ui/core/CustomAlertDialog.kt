package com.aptivist.tripsupiicsaapp.ui.core

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomAlertDialog(
    confirmText: String,
    dismissText: String? = null,
    title: String? = null,
    text: String? = null,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        title = { title?.let { Text(title) } },
        text = { text?.let { Text(text) } },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() }
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            dismissText?.let {
                TextButton(
                    onClick = { onDismiss() }
                ) {
                    Text(it)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CustomAlertDialogPreview() {
    CustomAlertDialog(
        confirmText = "Confirm",
        dismissText = "Dismiss",
        title = "Title",
        text = "This is a text",
        onDismiss = { },
        onConfirm = { },
    )
}