package com.czech.muvies.features.tv_shows

import androidx.lifecycle.*
import com.czech.muvies.models.TvShows
import com.czech.muvies.repository.TvShowsRepository
import com.czech.muvies.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TvShowsViewModel(
    private val repository: TvShowsRepository
) : ViewModel() {

    private val _tvShowsResponse = MutableLiveData<Result<List<TvShows.TvShowsResult>>>()
    val tvShowsResponse: LiveData<Result<List<TvShows.TvShowsResult>>> get() = _tvShowsResponse

    init {
        getAllTvShows()
    }

    private fun getAllTvShows() = viewModelScope.launch {
        combine(
            repository.getAiringToday().map { it.results },
            repository.getOnAir().map { it.results },
            repository.getPopular().map { it.results },
            repository.getTopRated().map { it.results },
            repository.getTrending().map { it.results }
        ) { airing, onAir, popular, topRated, trending ->
            airing.map {
                it.category = TvShows.TvShowsCategory.AIRING_TODAY
            }
            onAir.map {
                it.category = TvShows.TvShowsCategory.ON_AIR
            }
            popular.map {
                it.category = TvShows.TvShowsCategory.POPULAR
            }
            topRated.map {
                it.category = TvShows.TvShowsCategory.TOP_RATED
            }
            trending.map {
                it.category = TvShows.TvShowsCategory.TRENDING
            }
            listOf(airing, onAir, popular, topRated, trending).flatten()
        }.onStart { _tvShowsResponse.postValue(Result.Loading) }
            .catch { _tvShowsResponse.postValue(Result.Error(it)) }
            .flowOn(Dispatchers.IO)
            .collect { _tvShowsResponse.postValue(Result.Success(it)) }
    }
}

@Suppress("UNCHECKED_CAST")
class TvShowsViewModelFactory(
    private val repository: TvShowsRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvShowsViewModel::class.java)) {
            return TvShowsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
