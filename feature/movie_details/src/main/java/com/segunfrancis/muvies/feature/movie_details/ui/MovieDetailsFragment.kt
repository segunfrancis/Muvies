package com.segunfrancis.muvies.feature.movie_details.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.common.viewBinding
import com.segunfrancis.muvies.feature.movie_details.R
import com.segunfrancis.muvies.feature.movie_details.databinding.MovieDetailsFragmentBinding
import com.segunfrancis.muvies.feature.movie_details.di.MovieDetailsModule
import kotlinx.parcelize.Parcelize

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private val binding: MovieDetailsFragmentBinding by viewBinding(MovieDetailsFragmentBinding::bind)
    private val viewModel: MovieDetailsViewModel by viewModels {
        movieDetailsViewModelFactory(apiService = MovieDetailsModule.movieDetailsService)
    }
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            val uiState by viewModel.uiState.collectAsState()
            MuviesTheme {
                MovieDetailsScreen(response = uiState, onIntent = { intent ->
                    when (intent) {
                        is MovieDetailsIntents.ViewCastDetails -> {}
                        is MovieDetailsIntents.ViewMovieDetails -> {
                            findNavController().navigate(
                                MovieDetailsFragmentDirections.toMovieDetails(
                                    intent.movieTitle, intent.id, args.type
                                )
                            )
                        }
                    }
                })
            }
        }
    }
}

@Parcelize
class ToFavorites(
    var movieId: Int?,
    var movieTitle: String?,
    var movieOverview: String?,
    var moviePoster: String?,
    var movieBackdrop: String?,
    var movieDate: String?,
    var movieVote: Double?,
    var movieLang: String?
) : Parcelable
