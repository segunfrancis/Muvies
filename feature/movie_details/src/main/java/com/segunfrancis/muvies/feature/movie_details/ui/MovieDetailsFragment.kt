package com.segunfrancis.muvies.feature.movie_details.ui

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.common.viewBinding
import com.segunfrancis.muvies.feature.movie_details.R
import com.segunfrancis.muvies.feature.movie_details.databinding.MovieDetailsFragmentBinding
import com.segunfrancis.muvies.feature.movie_details.di.MovieDetailsModule
import com.segunfrancis.muvies.feature.movie_details.navigation.DetailsNav
import kotlinx.parcelize.Parcelize

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private val binding: MovieDetailsFragmentBinding by viewBinding(MovieDetailsFragmentBinding::bind)
    private val viewModel: MovieDetailsViewModel by viewModels {
        movieDetailsViewModelFactory(apiService = MovieDetailsModule.movieDetailsService)
    }
    private var detailsNav: DetailsNav? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            val uiState by viewModel.uiState.collectAsState()
            MuviesTheme {
                MovieDetailsScreen(response = uiState, onIntent = { intent ->
                    when (intent) {
                        is MovieDetailsIntents.ViewCastDetails -> {}
                        is MovieDetailsIntents.ViewMovieDetails -> {
                            detailsNav?.toMovieDetailsScreen2(intent.id, intent.movieTitle)
                        }
                    }
                })
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DetailsNav) {
            detailsNav = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        detailsNav = null
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
