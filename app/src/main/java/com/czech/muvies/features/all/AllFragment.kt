package com.czech.muvies.features.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.compose.collectAsLazyPagingItems
import com.czech.muvies.R
import com.czech.muvies.databinding.AllFragmentBinding
import com.czech.muvies.models.Movies.MoviesResult.MovieCategory
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.NavigationDeepLinks
import com.czech.muvies.utils.launchFragment
import com.segunfrancis.muvies.common.viewBinding

class AllFragment : Fragment(R.layout.all_fragment) {

    private val binding: AllFragmentBinding by viewBinding(AllFragmentBinding::bind)
    private val args: AllFragmentArgs by navArgs()
    private val movieCategory: MovieCategory by lazy {
        when (args.category) {
            MovieCategory.IN_THEATER.value -> MovieCategory.IN_THEATER
            MovieCategory.POPULAR.value -> MovieCategory.POPULAR
            MovieCategory.TOP_RATED.value -> MovieCategory.TOP_RATED
            MovieCategory.TRENDING.value -> MovieCategory.TRENDING
            else -> MovieCategory.UPCOMING
        }
    }
    private val apiService: MoviesApiService by lazy {
        MoviesApiService.getService()
    }
    private val viewModel: AllMoviesViewModel by viewModels {
        AllMoviesViewModelFactory(
            category = movieCategory,
            apiService = apiService
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent {
            val pagedList = viewModel.moviesFlow.collectAsLazyPagingItems()
            AllMoviesScreen(movies = pagedList, onItemClick = {
                launchFragment(
                    NavigationDeepLinks.toMovieDetails(
                        movieId = it.id,
                        movieTitle = it.title
                    )
                )
            })
        }
    }
}
