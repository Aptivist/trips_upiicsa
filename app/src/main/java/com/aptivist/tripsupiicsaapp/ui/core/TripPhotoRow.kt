package com.aptivist.tripsupiicsaapp.ui.core

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TripPhotoRow(
    photoUri: Uri?,
    onRemovePhoto: (Uri) -> Unit,
) {
    photoUri?.let {
        Row(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.LightGray)
                .padding(vertical = 2.dp)
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                GlideImage(
                    imageModel = { photoUri },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(
                    Modifier.width(
                        8.dp
                    )
                )
                photoUri.path?.let { path -> Text(path) }
            }
            IconButton(
                onClick = { onRemovePhoto(photoUri) }
            ) {
                Icon(Icons.Filled.Close, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripPhotoRowPreview() {
    TripPhotoRow(
        photoUri = null,
        onRemovePhoto = {}
    )
}