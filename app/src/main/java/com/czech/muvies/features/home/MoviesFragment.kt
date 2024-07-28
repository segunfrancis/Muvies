package com.czech.muvies.features.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.czech.muvies.R
import com.czech.muvies.databinding.MoviesFragmentBinding
import com.czech.muvies.di.InjectorUtils.ViewModelFactory
import com.czech.muvies.features.MainActivity
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.segunfrancis.muvies.common.Type
import com.segunfrancis.muvies.common.navigation.NavigationProvider
import com.segunfrancis.muvies.common.navigation.Navigator
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.common.viewBinding

class MoviesFragment : Fragment(R.layout.movies_fragment) {

    private val binding: MoviesFragmentBinding by viewBinding(MoviesFragmentBinding::bind)
    private val viewModel by viewModels<MoviesViewModel> {
        ViewModelFactory.provideMovieViewModelFactory()
    }
    private var navigator: Navigator? = null

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
                            navigator?.toMovieDetailsScreen(movieId = it.id, movieTitle = it.title, type = Type.Movie)
                        }, onSeeAllClick = {
                            navigator?.toAllMovies(category = it)
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
        if (requireActivity() is NavigationProvider) {
            navigator = (requireActivity() as NavigationProvider).getNavigator()
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
}
