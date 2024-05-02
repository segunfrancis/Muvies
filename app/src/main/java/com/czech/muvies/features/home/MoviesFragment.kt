package com.czech.muvies.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.czech.muvies.R
import com.czech.muvies.databinding.MoviesFragmentBinding
import com.czech.muvies.di.InjectorUtils.Navigator
import com.czech.muvies.di.InjectorUtils.ViewModelFactory
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.czech.muvies.utils.NavigationDeepLinks
import com.czech.muvies.utils.launchFragment
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.segunfrancis.muvies.common.viewBinding

class MoviesFragment : Fragment(R.layout.movies_fragment) {

    private val binding: MoviesFragmentBinding by viewBinding(MoviesFragmentBinding::bind)
    private val viewModel by viewModels<MoviesViewModel> {
        ViewModelFactory.provideMovieViewModelFactory()
    }
    private val navigator by lazy { Navigator.getAppNavigator(findNavController()) }

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
                            navigator.toMovieDetailsScreen(it.id, it.title)
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
}
