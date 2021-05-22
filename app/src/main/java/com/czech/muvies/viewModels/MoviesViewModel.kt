package com.czech.muvies.viewModels

import androidx.lifecycle.*
import com.czech.muvies.models.Movies
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.MovieRepository
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val TAG: String = "MoviesViewModel"

@ExperimentalCoroutinesApi
class MoviesViewModel(
    private val apiService: MoviesApiService,
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieResponse = MutableLiveData<Resource<List<Movies.MoviesResult>>>()
    val movieResponse: LiveData<Resource<List<Movies.MoviesResult>>> get() = _movieResponse

    init {
        getAllMovies()
    }

    private fun getAllMovies() = viewModelScope.launch {
        val inTheaterResponse = repository.getInTheaterMovies()
        val topRatedResponse = repository.getTopRatedMovies()
        val popularResponse = repository.getPopularMovies()
        val trendingResponse = repository.getTrendingMovies()
        val upComingResponse = repository.getUpComingMovies()
        combine(
            inTheaterResponse,
            topRatedResponse,
            popularResponse,
            trendingResponse,
            upComingResponse
        ) { inTheater, topRated, popular, trending, upComing ->
            inTheater.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.IN_THEATER
            }
            topRated.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.TOP_RATED
            }
            popular.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.POPULAR
            }
            trending.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.TRENDING
            }
            upComing.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.UPCOMING
            }
            listOf(
                inTheater.results,
                topRated.results,
                popular.results,
                trending.results,
                upComing.results
            ).flatten()
        }.onStart { _movieResponse.postValue(Resource.loading(emptyList())) }
            .catch { _movieResponse.postValue(Resource.error(emptyList(), "Something went wrong")) }
            .flowOn(Dispatchers.IO)
            .collect {
                _movieResponse.postValue(Resource.success(it))
            }
    }
}

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class MovieViewModelFactory(
    private val apiService: MoviesApiService,
    private val repository: MovieRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(apiService, repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}