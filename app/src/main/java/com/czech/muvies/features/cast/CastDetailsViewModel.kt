package com.czech.muvies.features.cast

import androidx.lifecycle.*
import com.czech.muvies.models.PersonDetails
import com.czech.muvies.models.PersonMovies
import com.czech.muvies.models.PersonTvShows
import com.czech.muvies.repository.CastRepository
import com.czech.muvies.utils.Result
import com.czech.muvies.utils.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CastDetailsViewModel(private val id: Int, private val repository: CastRepository) : ViewModel() {

    private var _castDetails = MutableLiveData<Result<PersonDetails>>()
    val castDetails get() = _castDetails.toLiveData()

    private var _movieDetails = MutableLiveData<Result<PersonMovies>>()
    val movieDetails get() = _movieDetails.toLiveData()

    private var _tvShowsDetails = MutableLiveData<Result<PersonTvShows>>()
    val tvShowsDetails get() = _tvShowsDetails.toLiveData()

    init {
        getCastDetails()
    }

    private fun getCastDetails() = viewModelScope.launch {
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
                _castDetails.value = Result.Success(it.first)
                _movieDetails.value = Result.Success(it.second)
                _tvShowsDetails.value = Result.Success(it.third)
            }
    }
}

@Suppress("UNCHECKED_CAST")
class CastDetailsViewModelFactory(private val castId: Int, private val repository: CastRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CastDetailsViewModel::class.java)) {
            return CastDetailsViewModel(id = castId, repository = repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
