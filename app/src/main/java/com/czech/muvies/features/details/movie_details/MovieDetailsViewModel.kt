package com.czech.muvies.features.details.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import com.czech.muvies.features.details.model.MovieDetailsResponse
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.MovieDetailsRepository
import com.czech.muvies.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val detailsRepository: MovieDetailsRepository
) : ViewModel() {

    private val _movieDetailsResponse = MutableLiveData<Result<MovieDetailsResponse>>()
    val movieDetails: LiveData<Result<MovieDetailsResponse>> get() = _movieDetailsResponse

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        combine(
            detailsRepository.getMoviesDetails(movieId).catch { throw Throwable(it) },
            detailsRepository.getCasts(movieId).catch { throw Throwable(it) },
            detailsRepository.getSimilarMovies(movieId).catch { throw Throwable(it) }
        ) { movieDetails, movieCredits, similarMovies, ->
            MovieDetailsResponse(details = movieDetails, credits = movieCredits, similarMovies = similarMovies)
        }.onStart { _movieDetailsResponse.postValue(Result.Loading) }
            .catch { _movieDetailsResponse.postValue(Result.Error(it)) }
            .flowOn(Dispatchers.IO)
            .collect { _movieDetailsResponse.postValue(Result.Success(it)) }
    }
}

@Suppress("UNCHECKED_CAST")
class MovieDetailsViewModelFactory(private val apiService: MoviesApiService) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(MovieDetailsRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
