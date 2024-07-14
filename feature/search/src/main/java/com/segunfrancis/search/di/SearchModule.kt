package com.segunfrancis.search.di

import com.segunfrancis.muvies.data.remote.di.RemoteModule
import com.segunfrancis.search.api.SearchApiService
import retrofit2.create

object SearchModule {

    fun provideSearchAPiService() = RemoteModule.retrofit.create<SearchApiService>()
}
