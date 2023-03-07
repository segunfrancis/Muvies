package com.czech.muvies.features.cast

import androidx.lifecycle.*
import com.czech.muvies.models.PersonDetails
import com.czech.muvies.models.PersonMovies
import com.czech.muvies.models.PersonTvShows
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.CastRepository
import com.czech.muvies.utils.Result
import com.czech.muvies.utils.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CastDetailsViewModel(private val repository: CastRepository) : ViewModel() {

    private var _castDetails = MutableLiveData<Result<PersonDetails>>()
    val castDetails get() = _castDetails.toLiveData()

    private var _movieDetails = MutableLiveData<Result<PersonMovies>>()
    val movieDetails get() = _movieDetails.toLiveData()

    private var _tvShowsDetails = MutableLiveData<Result<PersonTvShows>>()
    val tvShowsDetails get() = _tvShowsDetails.toLiveData()

    fun getCastDetails(id: Int) = viewModelScope.launch {
        combine(
            repository.getCastDetails(id).catch { throw Throwable(it) },
            repository.getCastMovies(id).catch { throw Throwable(it) },
            repository.getCastTvShows(id).catch { throw Throwable(it) }
        ) { details, movies, tvShows ->
            Triple(details, movies, tvShows)
        }.onStart {
            _castDetails.postValue(Result.Loading)
            _movieDetails.postValue(Result.Loading)
            _tvShowsDetails.postValue(Result.Loading)
        }.catch {
            _castDetails.postValue(Result.Error(it))
            _movieDetails.postValue(Result.Error(it))
            _tvShowsDetails.postValue(Result.Error(it))
        }.flowOn(Dispatchers.IO)
            .collect {
                _castDetails.postValue(Result.Success(it.first))
                _movieDetails.postValue(Result.Success(it.second))
                _tvShowsDetails.postValue(Result.Success(it.third))
            }
    }
}

@Suppress("UNCHECKED_CAST")
class CastDetailsViewModelFactory(private val service: MoviesApiService) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CastDetailsViewModel::class.java)) {
            return CastDetailsViewModel(CastRepository(service)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
