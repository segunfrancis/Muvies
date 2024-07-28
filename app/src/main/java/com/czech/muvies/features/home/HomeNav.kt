package com.czech.muvies.features.home

import com.segunfrancis.muvies.common.Movies.MoviesResult.MovieCategory
import com.segunfrancis.muvies.common.Type

interface HomeNav {

    fun toMovieDetailsScreen(movieId: Long, movieTitle: String, type: Type)

    fun toAllMovies(category: MovieCategory)
}
