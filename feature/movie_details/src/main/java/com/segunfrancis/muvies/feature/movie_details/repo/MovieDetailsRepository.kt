package com.segunfrancis.muvies.feature.movie_details.repo

import com.segunfrancis.muvies.feature.movie_details.api.MovieDetailsService
import com.segunfrancis.muvies.feature.movie_details.dto.MovieCreditsResponse
import com.segunfrancis.muvies.feature.movie_details.dto.MovieDetailsResponse
import com.segunfrancis.muvies.feature.movie_details.dto.SimilarMoviesResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MovieDetailsRepository(
    private val service: MovieDetailsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResult {
        return withContext(dispatcher) {
            val details = async { safeCall { service.getMovieDetails(movieId) } }
            val similarMovies = async { safeCall { service.getSimilarMovies(movieId).results } }
            val credits = async { safeCall { service.getMovieCredits(movieId) } }
            MovieDetailsResult(
                details = details.await(),
                similarMovies = similarMovies.await(),
                credits = credits.await()
            )
        }
    }
}

data class MovieDetailsResult(
    val details: Result<MovieDetailsResponse>? = null,
    val similarMovies: Result<List<SimilarMoviesResult>?>? = null,
    val credits: Result<MovieCreditsResponse>? = null
)

suspend fun <T> safeCall(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (t: Throwable) {
        Result.failure(t)
    }
}
