package com.czech.muvies.repository

import com.czech.muvies.models.TvShows
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowsRepository(private val moviesApiService: MoviesApiService) {

    fun getAiringToday(): Flow<TvShows> {
        return flow { emit(moviesApiService.getAiringTodayTVAsync()) }
    }

    fun getOnAir(): Flow<TvShows> {
        return flow { emit(moviesApiService.getOnAirTvAsync()) }
    }

    fun getPopular(): Flow<TvShows> {
        return flow { emit(moviesApiService.getPopularTVAsync()) }
    }

    fun getTopRated(): Flow<TvShows> {
        return flow { emit(moviesApiService.getTopRatedTVAsync()) }
    }

    fun getTrending(): Flow<TvShows> {
        return flow { emit(moviesApiService.getTrendingTVAsync()) }
    }
}
