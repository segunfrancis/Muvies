package com.segunfrancis.muvies.feature.movie_details.api

import com.segunfrancis.muvies.common.BaseResponse
import com.segunfrancis.muvies.feature.movie_details.BuildConfig
import com.segunfrancis.muvies.feature.movie_details.dto.MovieCreditsResponse
import com.segunfrancis.muvies.feature.movie_details.dto.MovieDetailsResponse
import com.segunfrancis.muvies.feature.movie_details.dto.SimilarMoviesResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsService {

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): BaseResponse<List<SimilarMoviesResult>>

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieCreditsResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDetailsResponse
}
