package com.aptivist.tripsupiicsaapp.ui.views

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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aptivist.tripsupiicsaapp.R

@Composable
fun AppDrawer() {
    AppDrawerContent()
}

@Composable
fun AppDrawerContent() {
    ModalDrawerSheet {
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.export_trips)) },
            selected = false,
            icon = { Icon(Icons.Filled.ArrowCircleUp, contentDescription = null) },
            onClick = { }
        )
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.share_trips)) },
            selected = false,
            icon = { Icon(Icons.Filled.Share, contentDescription = "") },
            onClick = { }
        )
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.import_trips)) },
            selected = false,
            icon = { Icon(Icons.Filled.ArrowCircleDown, contentDescription = "") },
            onClick = { }
        )
        HorizontalDivider()
        Spacer(Modifier.weight(1f))
        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 24.dp),
            onClick = { }
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
    AppDrawerContent()
}