package com.czech.muvies.features.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.czech.muvies.R
import com.czech.muvies.databinding.MoviesFragmentBinding
import com.czech.muvies.di.InjectorUtils.ViewModelFactory
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.common.viewBinding

class MoviesFragment : Fragment(R.layout.movies_fragment) {

    private val binding: MoviesFragmentBinding by viewBinding(MoviesFragmentBinding::bind)
    private val viewModel by viewModels<MoviesViewModel> {
        ViewModelFactory.provideMovieViewModelFactory()
    }
    private var homeNav: HomeNav? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieComposeView.setContent {
            MuviesTheme {
                val response = viewModel.uiState.value
                if (response.isLoading) {
                    binding.lottieProgress.makeVisible()
                } else {
                    binding.lottieProgress.makeGone()
                    if (response.movies.isNotEmpty()) {
                        HomeScreen(movies = response, onMovieItemClick = {
                            homeNav?.toMovieDetailsScreen(it.id, it.title)
                        }, onSeeAllClick = {
                            homeNav?.toAllMovies(category = it)
                        })
                    } else {
                        // todo: Handle error
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeNav) {
            homeNav = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        homeNav = null
    }
}
