package com.czech.muvies.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.czech.muvies.R
import com.czech.muvies.features.home.MoviesFragmentDirections
import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.muvies.common.Type
import com.segunfrancis.muvies.common.navigation.Navigator
import com.segunfrancis.search.ui.SearchFragmentDirections

class AppNavigator(private val activity: AppCompatActivity) : Navigator {

    override fun toMovieDetailsScreen(movieId: Long, movieTitle: String, type: Type) {
        activity.findNavController(R.id.nav_host_fragment).navigate(
            MoviesFragmentDirections.toDetails(
                movieTitle = movieTitle,
                movieOrSeriesId = movieId,
                type = type
            )
        )
    }

    override fun toAllMovies(category: Movies.MoviesResult.MovieCategory) {
        activity.findNavController(R.id.nav_host_fragment).navigate(MoviesFragmentDirections.toAllMovies(category))
    }

    override fun navigateToDetails(result: Movies.MoviesResult, type: Type) {
        activity.findNavController(R.id.nav_host_fragment).navigate(
            SearchFragmentDirections.toDetailsNavGraph(
                movieTitle = result.title.ifEmpty { result.name },
                movieOrSeriesId = result.id,
                type = type
            )
        )
    }
}
