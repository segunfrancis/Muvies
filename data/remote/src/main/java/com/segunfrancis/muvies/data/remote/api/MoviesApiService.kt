package com.segunfrancis.muvies.data.remote.api

import com.segunfrancis.muvies.common.CommonConstants.ENGLISH
import com.segunfrancis.muvies.data.remote.BuildConfig
import com.segunfrancis.muvies.data.remote.model.RemoteApiResponse
import com.segunfrancis.muvies.data.remote.model.RemoteMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = ENGLISH,
        @Query("page") page: Int = 1
    ): RemoteApiResponse
}
