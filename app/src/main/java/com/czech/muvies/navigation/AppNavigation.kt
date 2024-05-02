package com.czech.muvies.navigation

import androidx.navigation.NavController
import com.czech.muvies.features.home.HomeNav
import com.czech.muvies.features.home.MoviesFragmentDirections

class AppNavigation(private val navController: NavController) : HomeNav {
    override fun toMovieDetailsScreen(movieId: Int, movieTitle: String) {
        navController.navigate(MoviesFragmentDirections.toMovieDetails(movieId, movieTitle))
    }

    
}