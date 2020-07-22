package com.example.muvies.network

import android.os.Build
import com.example.muvies.BuildConfig
import com.example.muvies.LANGUAGE
import com.example.muvies.models.InTheatersMovies
import com.example.muvies.models.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL).build()

interface MoviesApiService {

    companion object {
        fun getService(): MoviesApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return  retrofit.create(MoviesApiService::class.java)
        }
    }

    @GET("movie/upcoming")
    fun getUpcomingMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<UpcomingMovies>>

    @GET("movie/popular")
    fun getPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<PopularMovies>>

    @GET("movie/top_rated")
    fun getTopRatedMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<TopRatedMovies>>

    @GET("movie/now_playing")
    fun getInTheatersMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<InTheatersMovies>>

    @GET("movie/now_playing")
    suspend fun getPagedInTheatersList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<InTheatersMovies>

    @GET("tv/airing_today")
    fun getAiringTodayTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<AiringTodayTV>>

    @GET("tv/on_the_air")
    fun getOnAirTvAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<OnAirTV>>

    @GET("tv/popular")
    fun getPopularTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<PopularTV>>

    @GET("tv/top_rated")
    fun getTopRatedTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<TopRatedTV>>

    @GET("discover/movie")
    fun getDiscoverAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ): Deferred<Response<Discover>>

    @GET("trending/movie/day")
    fun getTrendingMoviesAsync(
        @Query("api_key") apiKey: String
    ): Deferred<Response<TrendingMovies>>

    @GET("trending/tv/day")
    fun getTrendingTVAsync(
        @Query("api_key") apiKey: String
    ): Deferred<Response<TrendingTV>>
}

object MoviesApi {
    val retrofitService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}
