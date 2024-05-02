package com.segunfrancis.all_movies.api

import com.segunfrancis.all_movies.BuildConfig
import com.segunfrancis.muvies.common.CommonConstants.ENGLISH
import com.segunfrancis.muvies.common.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AllMoviesService {
    @GET("{first}/{second}")
    suspend fun getMoviesGeneric(
        @Path("first") firstPath: String,
        @Path(value = "second", encoded = true) secondPath: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = ENGLISH,
        @Query("page") page: Int,
    ): Movies
}
