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
    suspend fun getSimilarMovies(@Path("id") movieId: Long): BaseResponse<List<SimilarMoviesResult>>

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(@Path("id") movieId: Long): MovieCreditsResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Long): MovieDetailsResponse

    @GET("tv/{id}")
    suspend fun getTvShowDetails(@Path("id") movieId: Long): MovieDetailsResponse

    @GET("tv/{id}/credits")
    suspend fun getSeriesCredits(@Path("id") seriesId: Long): MovieCreditsResponse

    @GET("tv/{id}/similar")
    suspend fun getSimilarSeries(@Path("id") seriesId: Long): BaseResponse<List<SimilarMoviesResult>>
}
