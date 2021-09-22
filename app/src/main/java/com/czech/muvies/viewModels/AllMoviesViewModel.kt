package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.AllMoviesDataSourceFactory
import com.czech.muvies.models.Movies
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class AllMoviesViewModel(category: String) : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val airingTodayList: LiveData<PagedList<Movies.MoviesResult>>
    private val pageSize = 50
    private val allMoviesDataSourceFactory: AllMoviesDataSourceFactory =
        AllMoviesDataSourceFactory(apiService, Dispatchers.IO, category)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        airingTodayList = LivePagedListBuilder(allMoviesDataSourceFactory, config).build()
    }

    fun getAiringTodayList(): LiveData<PagedList<Movies.MoviesResult>> = airingTodayList
}

@Suppress("UNCHECKED_CAST")
class AllMoviesViewModelFactory(private val category: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AllMoviesViewModel::class.java)) {
            AllMoviesViewModel(category) as T
        } else throw IllegalArgumentException("Unknown class")
    }
}
