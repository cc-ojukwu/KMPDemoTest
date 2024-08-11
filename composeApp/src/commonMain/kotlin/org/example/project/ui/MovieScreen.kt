package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.di.IMAGE_BASE_URL
import org.example.project.getPlatform
import org.example.project.ui.component.DemoImage

@Composable
fun MovieScreen(
    id: Int,
    viewModel: MovieViewModel,
    onBackClick: () -> Unit
) {
    val platform = getPlatform()

    LaunchedEffect(Unit) {
        viewModel.getMovie(id)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when(val state = uiState) {
            is MovieUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is MovieUiState.Error -> {
                TextButton(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = { viewModel.getMovie(id) },
                    content = {
                        Text(
                            text = "Something went wrong. Tap to try again.",
                            textDecoration = TextDecoration.Underline
                        )
                    }
                )
            }
            is MovieUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) {
                        DemoImage(
                            modifier = Modifier.fillMaxSize(),
                            imageUrl = "$IMAGE_BASE_URL${state.movie.posterUrl}"
                        )
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 24.dp, start = 20.dp)
                                .background(
                                    color = MaterialTheme.colors.surface,
                                    shape = CircleShape
                                ),
                            onClick = onBackClick,
                            content = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .padding(horizontal = platform.homeScreenPadding),
                        text = "Title: ${state.movie.title}",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .padding(horizontal = platform.homeScreenPadding),
                        text = "Overview",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .padding(horizontal = platform.homeScreenPadding),
                        text = state.movie.overview,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
                    )
                    Text(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .padding(horizontal = platform.homeScreenPadding),
                        text = "Release date: ${state.movie.releaseDate}",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}