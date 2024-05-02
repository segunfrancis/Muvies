package com.segunfrancis.all_movies.di

import com.segunfrancis.all_movies.api.AllMoviesService
import com.segunfrancis.muvies.data.remote.di.RemoteModule
import retrofit2.create

object AllMoviesModule {
    fun provideApiService() = RemoteModule.retrofit.create<AllMoviesService>()
}
