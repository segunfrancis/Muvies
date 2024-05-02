package com.czech.muvies.navigation

import androidx.navigation.NavController
import com.czech.muvies.features.home.HomeNav
import com.czech.muvies.features.home.MoviesFragmentDirections
import com.segunfrancis.muvies.common.Movies.MoviesResult.MovieCategory

class AppNavigation(private val navController: NavController) : HomeNav {
    override fun toMovieDetailsScreen(movieId: Int, movieTitle: String) {
        navController.navigate(MoviesFragmentDirections.toMovieDetails(movieId, movieTitle))
    }

    override fun toAllMovies(category: MovieCategory) {
        navController.navigate(
            MoviesFragmentDirections.toAllMovies(
                category = category
            )
        )
    }
}
