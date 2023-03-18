package com.czech.muvies.features.details.tv_show_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.czech.muvies.features.details.model.TvShowsDetailsResponse
import com.czech.muvies.repository.TvShowsDetailsRepository
import com.czech.muvies.utils.Result
import com.czech.muvies.utils.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TvShowDetailsViewModel(private val repository: TvShowsDetailsRepository) : ViewModel() {

    private val _tvShowsResponse = MutableLiveData<Result<TvShowsDetailsResponse>>()
    val tvShowsResponse get() = _tvShowsResponse.toLiveData()

    fun getTvShowsDetails(id: Int) = viewModelScope.launch {
        combine(
            repository.getTvShowDetails(id).catch { throw Throwable(it) },
            repository.getSimilarTvShows(id).map { it.results }.catch { throw Throwable(it) },
            repository.getTvShowsCredit(id).map { it.cast }.catch { throw Throwable(it) }
        ) { details, similar, casts ->
            TvShowsDetailsResponse(tvShowDetails = details, similarTvShows = similar, cast = casts)
        }.onStart { _tvShowsResponse.postValue(Result.Loading) }
            .catch { _tvShowsResponse.postValue(Result.Error(it)) }
            .flowOn(Dispatchers.IO)
            .collect { _tvShowsResponse.value = Result.Success(it) }
    }
}

@Suppress("UNCHECKED_CAST")
class TvShowDetailsViewModelFactory(private val repository: TvShowsDetailsRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvShowDetailsViewModel::class.java)) {
            return TvShowDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
