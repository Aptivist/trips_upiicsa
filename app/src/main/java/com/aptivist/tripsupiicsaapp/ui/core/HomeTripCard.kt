package com.aptivist.tripsupiicsaapp.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptivist.tripsupiicsaapp.domain.models.LocationModel
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeTripCard(
    modifier: Modifier = Modifier,
    trip: TripModel,
) {
    Card(
        modifier = modifier,
        onClick = { },
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (trip.coverImageUrl.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            Icons.Filled.ImageNotSupported,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Gray
                        )
                        Text(
                            "Image Not Available", style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.Gray,
                            )
                        )
                    }
                } else {
                    GlideImage(
                        imageModel = { trip.coverImageUrl },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                        )
                    )
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x8A000000), //87% opacity
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        trip.name,
                        style = TextStyle(
                            color = Color.White
                        ),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        trip.destination,
                        style = TextStyle(
                            color = Color.White
                        ),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W300,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeTripCardPreview() {
    HomeTripCard(
        trip = TripModel(
            id = 1,
            name = "Trip to CDMX",
            destination = "Mexico",
            startDate = "",
            endDate = "",
            location = LocationModel(
                latitude = 0.0,
                longitude = 0.0,
            ),
            notes = "",
            coverImageUrl = "",
            photosUris = emptyList()
        )
    )
}