package com.czech.muvies.features.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.czech.muvies.models.Movies
import com.czech.muvies.models.Movies.MoviesResult.MovieCategory
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.paging.PagingDatasource
import kotlinx.coroutines.flow.Flow

class AllMoviesViewModel(private val apiService: MoviesApiService, private val category: MovieCategory) : ViewModel() {

    private val pageSize = 20

    var moviesFlow: Flow<PagingData<Movies.MoviesResult>>

    init {
        moviesFlow = getPagedList()
    }

    private fun getPagedList() = Pager(
        config = PagingConfig(pageSize = pageSize),
        pagingSourceFactory = {
            PagingDatasource(
                api = apiService,
                firstPath = category.getPaths().first,
                secondPath = category.getPaths().second
            )
        }
    ).flow.cachedIn(viewModelScope)

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
class AllMoviesViewModelFactory(private val apiService: MoviesApiService, private val category: MovieCategory) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AllMoviesViewModel::class.java)) {
            AllMoviesViewModel(apiService = apiService, category = category) as T
        } else throw IllegalArgumentException("Unknown class")
    }
}
