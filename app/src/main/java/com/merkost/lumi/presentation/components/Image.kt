package com.merkost.lumi.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.merkost.lumi.R

@Composable
fun MovieImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    movieTitle: String,
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = movieTitle,
        modifier = modifier,
        error = {
            Image(
                painter = painterResource(id = R.drawable.no_image_placeholder),
                contentDescription = "Error loading image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        },
        contentScale = ContentScale.Crop
    )
}