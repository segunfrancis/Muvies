package com.czech.muvies.repository

import com.czech.muvies.models.SimilarTvShows
import com.czech.muvies.models.TvShowCredits
import com.czech.muvies.models.TvShowDetails
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowsDetailsRepository(private val moviesApiService: MoviesApiService) {

    fun getTvShowDetails(id: Int): Flow<TvShowDetails> {
        return flow { emit(moviesApiService.getTvShowsDetails(id)) }
    }

    fun getSimilarTvShows(id: Int) : Flow<SimilarTvShows> {
        return flow { emit(moviesApiService.getSimilarTvShows(id)) }
    }

    fun getTvShowsCredit(id: Int): Flow<TvShowCredits> {
        return flow { emit(moviesApiService.getShowCredits(id)) }
    }
}
