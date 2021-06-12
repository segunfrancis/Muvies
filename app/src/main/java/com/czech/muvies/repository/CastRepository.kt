package com.czech.muvies.repository

import com.czech.muvies.models.PersonDetails
import com.czech.muvies.models.PersonMovies
import com.czech.muvies.models.PersonTvShows
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CastRepository(private val service: MoviesApiService) {
    fun getCastDetails(id: Int): Flow<PersonDetails> {
        return flow { emit(service.getCastDetails(id)) }
    }

    fun getCastMovies(id: Int): Flow<PersonMovies> {
        return flow { emit(service.getPersonMovies(id)) }
    }

    fun getCastTvShows(id: Int): Flow<PersonTvShows> {
        return flow { emit(service.getPersonTvShows(id)) }
    }
}
