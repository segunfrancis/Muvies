package com.czech.muvies.dataSources

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.czech.muvies.BuildConfig
import com.czech.muvies.utils.AppConstants.LANGUAGE
import com.czech.muvies.models.TvShows
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class AiringTodayDataSource(
    private val apiService: MoviesApiService,
    coroutineContext: CoroutineContext
) : PageKeyedDataSource<Int, TvShows.TvShowsResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShows.TvShowsResult>
    ) {
        scope.launch {
            try {
                val response = apiService.getPagedAiringTodayList(BuildConfig.API_KEY, LANGUAGE, 1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, null, 2)
                    }
                }
            } catch (e: Exception) {
                Timber.d("Failed to fetch airing today tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TvShows.TvShowsResult>
    ) {
        scope.launch {
            try {
                val response =
                    apiService.getPagedAiringTodayList(BuildConfig.API_KEY, LANGUAGE, params.key)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }
            } catch (e: Exception) {
                Timber.d("Failed to fetch airing today tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TvShows.TvShowsResult>
    ) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class AiringTodayDataSourceFactory(
    private val apiService: MoviesApiService,
    private val dispatcher: CoroutineDispatcher
) : DataSource.Factory<Int, TvShows.TvShowsResult>() {

    val airingTodayDataSourceLiveData = MutableLiveData<AiringTodayDataSource>()

    override fun create(): DataSource<Int, TvShows.TvShowsResult> {
        val airingTodayDataSource = AiringTodayDataSource(apiService, dispatcher)
        airingTodayDataSourceLiveData.postValue(airingTodayDataSource)
        return airingTodayDataSource
    }
}
