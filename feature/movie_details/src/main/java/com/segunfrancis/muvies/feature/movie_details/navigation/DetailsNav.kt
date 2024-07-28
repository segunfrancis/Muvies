package com.segunfrancis.muvies.feature.movie_details.navigation

import com.segunfrancis.muvies.common.Type

interface DetailsNav {
    fun toMovieDetailsScreen2(movieId: Long, movieTitle: String, type: Type)
}
