package com.czech.muvies.features.home

import com.segunfrancis.muvies.common.Movies.MoviesResult.MovieCategory

interface HomeNav {

    fun toMovieDetailsScreen(movieId: Int, movieTitle: String)

    fun toAllMovies(category: MovieCategory)
}
