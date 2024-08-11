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

class MovieViewModel(
    private val repository: DemoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState = _uiState.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieUiState.Loading
        )

    fun getMovie(id: Int) {
        _uiState.update { MovieUiState.Loading }
        viewModelScope.launch {
            val result = repository.getMovie(id)
            if (result is Result.Error) {
                _uiState.update { MovieUiState.Error }
            } else {
                _uiState.update { MovieUiState.Success((result as Result.Success).data) }
            }
        }
    }
}

sealed interface MovieUiState {
    data object Loading: MovieUiState
    data object Error: MovieUiState
    data class Success(
        val movie: MovieResponse
    ) : MovieUiState
}