package com.aptivist.tripsupiicsaapp.ui.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptivist.tripsupiicsaapp.R

@Composable
fun HomeTripCard(
    modifier: Modifier = Modifier,
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
                Image(
                    painter = painterResource(R.drawable.beach),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
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
                        "Trip to Miami",
                        style = TextStyle(
                            color = Color.White
                        ),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        "Florida",
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
    HomeTripCard()
}