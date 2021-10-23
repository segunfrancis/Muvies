package com.segunfrancis.muvies.data.remote.di

import com.google.gson.Gson
import com.segunfrancis.muvies.common.CommonConstants.BASE_URL
import com.segunfrancis.muvies.common.CommonConstants.NETWORK_TIMEOUT
import com.segunfrancis.muvies.data.remote.api.MoviesApiService
import com.segunfrancis.muvies.data.remote.repository.RemoteRepositoryImpl
import com.segunfrancis.muvies.domain.repository.RemoteRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single { Gson().newBuilder().setLenient().create() }

    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(MoviesApiService::class.java)
    }

    factory<RemoteRepository> { RemoteRepositoryImpl(get()) }

}
