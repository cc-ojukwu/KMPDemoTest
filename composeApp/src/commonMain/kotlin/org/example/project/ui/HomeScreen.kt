package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.di.IMAGE_BASE_URL
import org.example.project.getPlatform
import org.example.project.model.MovieResponse
import org.example.project.ui.component.DemoImage

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToMovie: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )
    val platform = getPlatform()

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Movies",
                        textAlign = TextAlign.Center
                    )
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when(val state = uiState) {
                    is HomeUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is HomeUiState.Error -> {
                        TextButton(
                            modifier = Modifier.align(Alignment.Center),
                            onClick = { viewModel.retry() },
                            content = {
                                Text(
                                    text = "Something went wrong. Tap to try again.",
                                    textDecoration = TextDecoration.Underline
                                )
                            }
                        )
                    }
                    is HomeUiState.Success -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(36.dp),
                            contentPadding = PaddingValues(horizontal = platform.homeScreenPadding, vertical = 24.dp)
                        ) {
                            items(state.movies) { movie ->
                                MovieView(movie) { id ->
                                    onNavigateToMovie(id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun MovieView(
    movie: MovieResponse,
    onClick: (Int) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(movie.id)
                    }
                    .aspectRatio(1.4f),
            ) {
                DemoImage(
                    modifier = Modifier.fillMaxSize(),
                    imageUrl = "${IMAGE_BASE_URL}${movie.posterUrl}"
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black)
                            )
                        )
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    text = movie.overview,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.surface
                )
            }
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                text = "Title: ${movie.title}",
                style = MaterialTheme.typography.h6
            )
            Text(
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
                text = "Release date: ${movie.releaseDate}",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

