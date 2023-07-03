package com.czech.muvies.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.czech.muvies.models.Movies
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.roundUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SearchViewModel(private val api: MoviesApiService) : ViewModel() {

    var uiState = MutableStateFlow<SearchMoviesUiState>(SearchMoviesUiState.DefaultState)
        private set

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _searchFieldValue = MutableStateFlow("")
    val searchFieldValue = _searchFieldValue.asStateFlow()

    fun searchMovie(movieTitle: String) {
        _isLoading.update { true }
        viewModelScope.launch {
            try {
                val response =
                    withContext(Dispatchers.IO) { api.searchMovie(movieTitle = movieTitle) }
                val mappedResult = response.results.map {
                    it.copy(voteAverage = it.voteAverage.roundUp(decimalPlaces = 1))
                }
                if (mappedResult.isNotEmpty()) {
                    uiState.update { SearchMoviesUiState.Content(mappedResult) }
                } else {
                    uiState.update { SearchMoviesUiState.EmptyContent }
                }
                _isLoading.update { false }
            } catch (t: Throwable) {
                t.localizedMessage?.let { _error.emit(it) }
                _isLoading.update { false }
                Timber.e(t)
            }
        }
    }

    fun onSearchFieldChange(value: String) {
        _searchFieldValue.update { value }
    }
}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val api: MoviesApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}

sealed interface SearchMoviesUiState {
    data class Content(val movies: List<Movies.MoviesResult>) : SearchMoviesUiState
    object EmptyContent : SearchMoviesUiState
    object DefaultState : SearchMoviesUiState
}
