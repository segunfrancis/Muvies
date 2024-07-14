package com.segunfrancis.search.api

import com.segunfrancis.muvies.common.CommonConstants.ENGLISH
import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.search.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApiService {
    @GET("search/{type}")
    suspend fun searchMovie(
        @Path("type") searchType: String,
        @Query("query") movieTitle: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = ENGLISH,
        @Query("page") page: Int
    ): Movies
}
