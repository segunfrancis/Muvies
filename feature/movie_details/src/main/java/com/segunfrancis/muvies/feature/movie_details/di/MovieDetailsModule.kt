package com.segunfrancis.muvies.feature.movie_details.di

import com.segunfrancis.muvies.data.remote.di.RemoteModule
import com.segunfrancis.muvies.feature.movie_details.api.MovieDetailsService
import retrofit2.create

object MovieDetailsModule {

    val movieDetailsService = RemoteModule.retrofit.create<MovieDetailsService>()

}
