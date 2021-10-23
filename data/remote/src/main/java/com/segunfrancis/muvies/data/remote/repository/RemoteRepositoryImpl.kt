package com.segunfrancis.muvies.data.remote.repository

import com.segunfrancis.muvies.data.remote.api.MoviesApiService
import com.segunfrancis.muvies.data.remote.util.mapApiToDomain
import com.segunfrancis.muvies.domain.model.DomainApiResponse
import com.segunfrancis.muvies.domain.repository.RemoteRepository

class RemoteRepositoryImpl(private val apiService: MoviesApiService): RemoteRepository {
    override suspend fun getMovies(category: String, page: Int): DomainApiResponse {
        return apiService.getMovies(category = category, page = page).mapApiToDomain()
    }
}
