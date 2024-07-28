package com.segunfrancis.muvies.common.navigation

import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.muvies.common.Type

interface Navigator {
    fun toMovieDetailsScreen(movieId: Long, movieTitle: String, type: Type)

    fun toAllMovies(category: Movies.MoviesResult.MovieCategory)

    // search flow
    fun navigateToDetails(result: Movies.MoviesResult, type: Type)
}
