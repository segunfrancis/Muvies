package com.segunfrancis.muvies.domain.repository

import com.segunfrancis.muvies.domain.model.DomainApiResponse

interface RemoteRepository {
    suspend fun getMovies(category: String, page: Int): DomainApiResponse
}
