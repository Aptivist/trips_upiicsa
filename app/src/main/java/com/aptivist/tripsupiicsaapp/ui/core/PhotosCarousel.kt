package com.aptivist.tripsupiicsaapp.ui.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aptivist.tripsupiicsaapp.domain.models.TripPhotoModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosCarousel(
    modifier: Modifier = Modifier,
    photos: List<TripPhotoModel>
) {
    //Crea un Carousel de fotografías del Trip.
    //El HorizontalMultiBrowseCarousel pertenece a Material 3.

    val carouselState = rememberCarouselState { photos.count() }

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, bottom = 16.dp),
        preferredItemWidth = 200.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { item ->
        val item = photos[item]
        GlideImage(
            modifier = Modifier
                .height(250.dp)
                .maskClip(MaterialTheme.shapes.extraLarge),
            imageModel = { item.url },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosCarouselPreview() {
    PhotosCarousel(
        photos = listOf()
    )
}