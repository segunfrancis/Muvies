package com.segunfrancis.muvies.feature.movie_details.repo

import com.segunfrancis.muvies.common.Type
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

    suspend fun getDetails(id: Long, type: Type): DetailsResult {
        return when (type) {
            Type.Movie -> {
                withContext(dispatcher) {
                    val details = async { safeCall { service.getMovieDetails(id) } }
                    val similarMovies = async { safeCall { service.getSimilarMovies(id).results } }
                    val credits = async { safeCall { service.getMovieCredits(id) } }
                    DetailsResult(
                        details = details.await(),
                        similarMovies = similarMovies.await(),
                        credits = credits.await()
                    )
                }
            }
            Type.TvShow -> {
                withContext(dispatcher) {
                    val details = async { safeCall { service.getTvShowDetails(id) } }
                    val similarMovies = async { safeCall { service.getSimilarSeries(id).results } }
                    val credits = async { safeCall { service.getSeriesCredits(id) } }
                    DetailsResult(
                        details = details.await(),
                        similarMovies = similarMovies.await(),
                        credits = credits.await()
                    )
                }
            }
        }
    }
}

data class DetailsResult(
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
