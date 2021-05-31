package com.czech.muvies.viewModels

import androidx.lifecycle.*
import com.czech.muvies.models.MovieCredits
import com.czech.muvies.models.MovieDetails
import com.czech.muvies.models.SimilarMovies
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.MovieDetailsRepository
import com.czech.muvies.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val detailsRepository: MovieDetailsRepository
) : ViewModel() {

    private val _movieDetailsResponse = MutableLiveData<Result<MovieDetails>>()
    val movieDetails: LiveData<Result<MovieDetails>> get() = _movieDetailsResponse

    private val _movieCreditResponse = MutableLiveData<Result<MovieCredits>>()
    val movieCredits: LiveData<Result<MovieCredits>> get() = _movieCreditResponse

    private val _similarMovieResponse = MutableLiveData<Result<SimilarMovies>>()
    val similarMovies: LiveData<Result<SimilarMovies>> get() = _similarMovieResponse

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        detailsRepository.getMoviesDetails(movieId)
            .onStart { _movieDetailsResponse.postValue(Result.Loading) }
            .catch { _movieDetailsResponse.postValue(Result.Error(it)) }
            .flowOn(Dispatchers.IO)
            .collect { _movieDetailsResponse.postValue(Result.Success(it)) }
    }

    fun getCasts(movieId: Int) = viewModelScope.launch {
        detailsRepository.getCasts(movieId)
            .onStart { _movieCreditResponse.postValue(Result.Loading) }
            .catch { _movieCreditResponse.postValue(Result.Error(it)) }
            .flowOn(Dispatchers.IO)
            .collect { _movieCreditResponse.postValue(Result.Success(it)) }
    }

    fun getSimilarMovies(movieId: Int) = viewModelScope.launch {
        detailsRepository.getSimilarMovies(movieId)
            .onStart { _similarMovieResponse.postValue(Result.Loading) }
            .catch { _similarMovieResponse.postValue(Result.Error(it)) }
            .flowOn(Dispatchers.IO)
            .collect { _similarMovieResponse.postValue(Result.Success(it)) }
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
