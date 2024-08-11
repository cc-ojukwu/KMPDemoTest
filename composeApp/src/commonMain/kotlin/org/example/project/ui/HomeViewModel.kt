package org.example.project.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.repository.DemoRepository
import org.example.project.model.MovieResponse
import org.example.project.utils.Result

class HomeViewModel(
    private val repository: DemoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        _uiState.update { HomeUiState.Loading }
        viewModelScope.launch {
            val result = repository.getMovieCollection(page = 1)
            if (result is Result.Error) {
                _uiState.update { HomeUiState.Error }
            } else {
                _uiState.update { HomeUiState.Success((result as Result.Success).data.results) }
            }
        }
    }

    fun retry() {
        fetchMovies()
    }
}

sealed interface HomeUiState {
    data object Loading: HomeUiState
    data object Error: HomeUiState
    data class Success(
        val movies: List<MovieResponse>
    ) : HomeUiState
}