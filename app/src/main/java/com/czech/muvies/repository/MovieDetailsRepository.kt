package com.czech.muvies.repository

import com.czech.muvies.models.MovieCredits
import com.czech.muvies.models.MovieDetails
import com.czech.muvies.models.SimilarMovies
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDetailsRepository(private val apiService: MoviesApiService) {
    fun getMoviesDetails(movieId: Int): Flow<MovieDetails> {
        return flow { emit(apiService.getMovieDetails(movieId)) }
    }

    fun getCasts(movieId: Int): Flow<MovieCredits> {
        return flow { emit(apiService.getMovieCredits(movieId)) }
    }

    fun getSimilarMovies(movieId: Int): Flow<SimilarMovies> {
        return flow { emit(apiService.getSimilarMovies(movieId)) }
    }
}