package com.czech.muvies.dataSources

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.czech.muvies.network.MoviesApiService
import com.segunfrancis.muvies.common.Movies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class AllMoviesDataSource(
    private val apiService: MoviesApiService,
    coroutineContext: CoroutineContext,
    private val category: String
) : PageKeyedDataSource<Int, Movies.MoviesResult>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job + exceptionHandler)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movies.MoviesResult>
    ) {
        scope.launch {
            try {
                val response = apiService.getPagedMovieList(category = category, page = 1)
                callback.onResult(response.results, null, 2)
            } catch (e: Exception) {
                Timber.d("Failed to fetch airing today tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movies.MoviesResult>
    ) {
        scope.launch {
            try {
                val response = apiService.getPagedMovieList(category = category, page = params.key)
                callback.onResult(response.results, params.key + 1)
            } catch (e: Exception) {
                Timber.d("Failed to fetch airing today tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movies.MoviesResult>
    ) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class AllMoviesDataSourceFactory(
    private val apiService: MoviesApiService,
    private val dispatcher: CoroutineDispatcher,
    private val category: String
) : DataSource.Factory<Int, Movies.MoviesResult>() {

    val airingTodayDataSourceLiveData = MutableLiveData<AllMoviesDataSource>()

    override fun create(): DataSource<Int, Movies.MoviesResult> {
        val airingTodayDataSource = AllMoviesDataSource(apiService, dispatcher, category)
        airingTodayDataSourceLiveData.postValue(airingTodayDataSource)
        return airingTodayDataSource
    }
}
