package com.czech.muvies.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.czech.muvies.R
import com.czech.muvies.databinding.MoviesFragmentBinding
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.MovieRepository
import com.czech.muvies.utils.NavigationDeepLinks
import com.czech.muvies.utils.launchFragment
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.segunfrancis.muvies.common.viewBinding

class MoviesFragment : Fragment(R.layout.movies_fragment) {

    private val binding: MoviesFragmentBinding by viewBinding(MoviesFragmentBinding::bind)
    private val viewModel by viewModels<MoviesViewModel> { MovieViewModelFactory(MovieRepository((MoviesApiService.getService()))) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieComposeView.setContent {
            val response = viewModel.uiState.value
            if (response.isLoading) {
                binding.lottieProgress.makeVisible()
            } else {
                binding.lottieProgress.makeGone()
                if (response.movies.isNotEmpty()) {
                    HomeScreen(movies = response, onMovieItemClick = {
                        launchFragment(
                            NavigationDeepLinks.toMovieDetails(
                                movieId = it.id,
                                movieTitle = it.title
                            )
                        )
                    }, onSeeAllClick = {
                        launchFragment(
                            NavigationDeepLinks.toAllMovies(
                                category = it.value,
                                movieTitle = it.formattedName
                            )
                        )
                    })
                } else {
                    // todo: Handle error
                }
            }
        }
    }
}
