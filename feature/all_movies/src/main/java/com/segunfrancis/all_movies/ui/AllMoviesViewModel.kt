package com.segunfrancis.all_movies.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.segunfrancis.all_movies.PagingDatasource
import com.segunfrancis.all_movies.api.AllMoviesService
import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.muvies.common.Movies.MoviesResult.MovieCategory
import com.segunfrancis.muvies.common.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class AllMoviesViewModel(
    private val apiService: AllMoviesService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pageSize = 20
    private val _uiState = MutableStateFlow(AllMoviesModel())
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<MovieCategory>("category")?.let { category ->
            _uiState.update { it.copy(moviesFlow = getPagedList(category)) }
        }
    }

    private fun getPagedList(category: MovieCategory) = Pager(
        config = PagingConfig(pageSize = pageSize),
        pagingSourceFactory = {
            PagingDatasource(
                api = apiService,
                firstPath = category.getPaths().first,
                secondPath = category.getPaths().second
            )
        }
    ).flow
        .catch { error -> _uiState.update { it.copy(error = error.handleError()) } }
        .cachedIn(viewModelScope)

    private fun MovieCategory.getPaths(): Pair<String, String> {
        return when (this) {
            MovieCategory.IN_THEATER -> Pair(first = "movie", second = "now_playing")
            MovieCategory.UPCOMING -> Pair(first = "movie", second = "upcoming")
            MovieCategory.POPULAR -> Pair(first = "movie", second = "popular")
            MovieCategory.TOP_RATED -> Pair(first = "movie", second = "top_rated")
            MovieCategory.TRENDING -> Pair(first = "trending", second = "movie/day")
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun allMoviesViewModelFactory(apiService: AllMoviesService) = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return if (modelClass.isAssignableFrom(AllMoviesViewModel::class.java)) {
            AllMoviesViewModel(
                apiService = apiService,
                savedStateHandle = extras.createSavedStateHandle()
            ) as T
        } else throw IllegalArgumentException("Class ${modelClass.canonicalName} cannot be assigned")
    }
}

data class AllMoviesModel(
    val moviesFlow: Flow<PagingData<Movies.MoviesResult>>? = null,
    val error: String? = null
)
