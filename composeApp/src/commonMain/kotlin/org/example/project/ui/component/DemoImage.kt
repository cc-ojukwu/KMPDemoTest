package org.example.project.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest

@Composable
fun DemoImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
) {
    val request = ImageRequest.Builder(LocalPlatformContext.current)
        .data(imageUrl)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .build()
    AsyncImage(
        modifier = modifier,
        model = request,
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}