package com.czech.muvies.repository

import com.czech.muvies.network.MoviesApiService
import com.segunfrancis.muvies.common.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(private val service: MoviesApiService) {
    fun getInTheaterMovies(): Flow<Movies> {
        return flow { emit(service.getInTheatersMoviesAsync()) }
    }

    fun getUpComingMovies(): Flow<Movies> {
        return flow { emit(service.getUpcomingMoviesAsync()) }
    }

    fun getTopRatedMovies(): Flow<Movies> {
        return flow { emit(service.getTopRatedMoviesAsync()) }
    }

    fun getPopularMovies(): Flow<Movies> {
        return flow { emit(service.getPopularMoviesAsync()) }
    }

    fun getTrendingMovies(): Flow<Movies> {
        return flow { emit(service.getTrendingMoviesAsync()) }
    }
}