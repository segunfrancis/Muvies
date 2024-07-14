package com.segunfrancis.muvies.data.remote.di

import com.google.gson.Gson
import com.segunfrancis.muvies.common.CommonConstants.BASE_URL
import com.segunfrancis.muvies.common.CommonConstants.NETWORK_TIMEOUT
import com.segunfrancis.muvies.data.remote.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteModule {
    private val gson = Gson().newBuilder().setLenient().create()

    private val loggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val httpClient = OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor)
        .callTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}").build()
            it.proceed(request)
        }
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}
