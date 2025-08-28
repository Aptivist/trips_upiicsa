package com.aptivist.tripsupiicsaapp.ui.views

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UpsertTripView(
    tripId: Long,
) {

    LaunchedEffect(tripId) {
        if (tripId != -1L) {
            //LOAD TRIP
        }
    }

    UpsertTripContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertTripContent() {
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
        Box(modifier = Modifier.padding(innerPadding)) { }
    }
}

@Preview
@Composable
fun UpsertTripPreview() {

}

