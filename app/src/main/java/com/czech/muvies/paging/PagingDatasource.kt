package com.czech.muvies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.czech.muvies.models.Movies
import com.czech.muvies.network.MoviesApiService

class PagingDatasource(
    private val api: MoviesApiService,
    private val firstPath: String,
    private val secondPath: String
) : PagingSource<Int, Movies.MoviesResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies.MoviesResult> {
        return try {
            val nextPage = params.key ?: 1
            val response =
                api.getMoviesGeneric(page = nextPage, firstPath = firstPath, secondPath = secondPath)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = response.page + 1
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movies.MoviesResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
