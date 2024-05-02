package com.lydia.contact.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ContactImage(
    modifier: Modifier,
    imageUrl: String,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = imageUrl,
        imageLoader = ImageLoader(LocalContext.current),
        modifier = modifier.fillMaxSize(),
    )
}