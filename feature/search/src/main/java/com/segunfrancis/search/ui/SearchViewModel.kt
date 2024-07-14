package com.segunfrancis.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.muvies.common.roundUp
import com.segunfrancis.search.api.SearchApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val api: SearchApiService,
    private val reducer: SearchReducer
) : ViewModel() {

    var uiState = MutableStateFlow(SearchMoviesUiState())
        private set

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    fun processIntent(intent: SearchScreenIntents) {
        when (intent) {
            is SearchScreenIntents.OnSearchClick -> searchMovie(intent.searchQuery)
            is SearchScreenIntents.OnSearchFieldChange -> updateSearchField(intent.value)
            is SearchScreenIntents.OnTypeClick -> updateSearchType(intent.searchType)
        }
    }

    private fun searchMovie(movieTitle: String) {
        handleActions(SearchScreenActions.ShowLoader)
        viewModelScope.launch {
            try {
                val response =
                    withContext(Dispatchers.IO) {
                        api.searchMovie(
                            searchType = uiState.value.type.value,
                            movieTitle = movieTitle,
                            page = 1
                        )
                    }
                val mappedResult = response.results.map {
                    it.copy(voteAverage = it.voteAverage.roundUp(decimalPlaces = 1))
                }
                if (mappedResult.isNotEmpty()) {
                    handleActions(SearchScreenActions.ShowMovies(mappedResult))
                } else {
                    handleActions(SearchScreenActions.ShowNoMovies)
                }
            } catch (t: Throwable) {
                t.localizedMessage?.let { _error.emit(it) }
                handleActions(SearchScreenActions.ShowError(t))
            }
        }
    }

    private fun updateSearchField(value: String) {
        handleActions(SearchScreenActions.UpdateSearchText(value))
    }

    private fun updateSearchType(searchType: SearchType) {
        handleActions(SearchScreenActions.UpdateSearchType(searchType))
    }

    private fun handleActions(action: SearchScreenActions) {
        uiState.update { currentState ->
            reducer(action, currentState)
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun searchViewModelFactory(api: SearchApiService) = object : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(api, SearchReducer()) as T
        }
        throw IllegalArgumentException("Class ${modelClass.canonicalName} is not assignable")
    }
}

data class SearchMoviesUiState(
    val movies: List<Movies.MoviesResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchText: String = "",
    val type: SearchType = SearchType.Movies
)

sealed interface SearchScreenActions {
    data class ShowMovies(val movies: List<Movies.MoviesResult>) : SearchScreenActions
    data object ShowNoMovies : SearchScreenActions
    data class ShowError(val error: Throwable) : SearchScreenActions
    data object ShowLoader : SearchScreenActions
    data class UpdateSearchText(val searchText: String) : SearchScreenActions
    data class UpdateSearchType(val searchType: SearchType) : SearchScreenActions
}
