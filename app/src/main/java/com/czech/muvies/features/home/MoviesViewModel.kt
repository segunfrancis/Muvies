package com.czech.muvies.features.home

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.czech.muvies.models.Movies
import com.czech.muvies.repository.MovieRepository
import com.czech.muvies.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieResponse = MutableLiveData<Result<List<Movies.MoviesResult>>>()
    val movieResponse: LiveData<Result<List<Movies.MoviesResult>>> get() = _movieResponse

    init {
        getAllMovies()
    }

    private fun getAllMovies() = viewModelScope.launch {
        combine(
            repository.getInTheaterMovies(),
            repository.getTopRatedMovies(),
            repository.getPopularMovies(),
            repository.getTrendingMovies(),
            repository.getUpComingMovies()
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
        }.onStart { _movieResponse.postValue(Result.Loading) }
            .catch { _movieResponse.postValue(Result.Error(it)) }
            .flowOn(Dispatchers.IO)
            .collect {
                _movieResponse.postValue(Result.Success(it))
            }
    }
}

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
