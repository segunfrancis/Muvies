package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.AiringTodayDataSourceFactory
import com.czech.muvies.models.TvShows
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class AiringTodayViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val airingTodayList: LiveData<PagedList<TvShows.TvShowsResult>>
    private val pageSize = 50
    private val airingTodayDataSourceFactory: AiringTodayDataSourceFactory =
        AiringTodayDataSourceFactory(apiService, Dispatchers.IO)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        airingTodayList = LivePagedListBuilder(airingTodayDataSourceFactory, config).build()
    }

    fun getAiringTodayList(): LiveData<PagedList<TvShows.TvShowsResult>> = airingTodayList
}