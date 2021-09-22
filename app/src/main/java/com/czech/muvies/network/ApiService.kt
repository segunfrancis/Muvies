package com.czech.muvies.network

import com.czech.muvies.BuildConfig
import com.czech.muvies.utils.AppConstants.LANGUAGE
import com.czech.muvies.models.Movies
import com.czech.muvies.models.*
import com.czech.muvies.utils.AppConstants
import com.czech.muvies.utils.AppConstants.NETWORK_TIMEOUT
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MoviesApiService {

    companion object {
        fun getService(): MoviesApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient().newBuilder().addInterceptor(loggingInterceptor)
                .callTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MoviesApiService::class.java)
        }
    }

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1,
    ): Movies

    @GET("movie/upcoming")
    suspend fun getPagedUpcomingList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("movie/popular")
    suspend fun getPopularMoviesAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ): Movies

    @GET("movie/popular")
    suspend fun getPagedPopularList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ): Movies

    @GET("movie/top_rated")
    suspend fun getPagedTopRatedMoviesList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("movie/now_playing")
    suspend fun getInTheatersMoviesAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ): Movies

    @GET("movie/now_playing")
    suspend fun getPagedInTheatersList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("tv/airing_today")
    suspend fun getAiringTodayTVAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ): TvShows

    @GET("tv/airing_today")
    suspend fun getPagedAiringTodayList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("movie/{category}")
    suspend fun getPagedMovieList(
        @Path("category") category: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Movies

    @GET("tv/on_the_air")
    suspend fun getOnAirTvAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ): TvShows

    @GET("tv/on_the_air")
    suspend fun getPagedOnAirTvList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("tv/popular")
    suspend fun getPopularTVAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ): TvShows

    @GET("tv/popular")
    suspend fun getPagedPopularTVList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("tv/top_rated")
    suspend fun getTopRatedTVAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ): TvShows

    @GET("tv/top_rated")
    suspend fun getPagedTopRatedTVList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("trending/movie/day")
    suspend fun getTrendingMoviesAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Movies

    @GET("trending/movie/day")
    suspend fun getPagedTrendingMoviesList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("trending/tv/day")
    suspend fun getTrendingTVAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): TvShows

    @GET("trending/tv/day")
    suspend fun getPagedTrendingTVList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE
    ): MovieDetails

    @GET("tv/{id}")
    suspend fun getTvShowsDetails(
        @Path("id") tvShowsId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE
    ): TvShowDetails

    @GET("tv/{id}/season/{season}")
    suspend fun getSeasonDetails(
        @Path("id") tvShowsId: Int,
        @Path("season") seasonNum: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE
    ): SeasonDetails

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<SimilarMovies>

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): SimilarMovies

    @GET("tv/{id}/similar")
    suspend fun getSimilarTvShowsResponse(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<SimilarTvShows>

    @GET("tv/{id}/similar")
    suspend fun getSimilarTvShows(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ): SimilarTvShows

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieCredits

    @GET("person/{id}")
    suspend fun getCastDetails(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE
    ): PersonDetails

    @GET("tv/{id}/credits")
    suspend fun getShowCredits(
        @Path("id") showId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE
    ): TvShowCredits

    @GET("person/{id}/movie_credits")
    suspend fun getPersonMovies(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE
    ): PersonMovies

    @GET("person/{id}/tv_credits")
    suspend fun getPersonTvShows(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE
    ): PersonTvShows
}
