package com.czech.muvies.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.czech.muvies.R
import com.czech.muvies.databinding.MoviesFragmentBinding
import com.czech.muvies.features.home.epoxy.MainMovieHolder
import com.czech.muvies.features.home.epoxy.MovieHeaderHolder
import com.czech.muvies.features.home.epoxy.SubMovieHolder
import com.czech.muvies.models.*
import com.czech.muvies.models.Movies.MoviesResult.*
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.MovieRepository
import com.czech.muvies.utils.*
import com.czech.muvies.utils.epoxy.BaseEpoxyController
import com.czech.muvies.utils.epoxy.carouselNoSnap
import com.segunfrancis.muvies.common.viewBinding
import timber.log.Timber

class MoviesFragment : Fragment(R.layout.movies_fragment) {

    private val binding: MoviesFragmentBinding by viewBinding(MoviesFragmentBinding::bind)
    private val viewModel by viewModels<MoviesViewModel> { MovieViewModelFactory(MovieRepository((MoviesApiService.getService()))) }
    private val controller: MovieAdapterController by lazy { MovieAdapterController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieEpoxyRecyclerView.adapter = controller.adapter

        viewModel.movieResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> handleLoading()
                is Result.Error -> handleError(result.error)
                is Result.Success -> handleSuccess(result.data)
            }
        }
    }

    private fun handleError(error: Throwable) = with(binding) {
        root.showErrorMessage(error.localizedMessage)
        lottieProgress.makeGone()
        Timber.e(error)
    }

    private fun handleLoading() {
        binding.lottieProgress.makeVisible()
    }

    private fun handleSuccess(data: List<Movies.MoviesResult>) {
        binding.lottieProgress.makeGone()
        controller.data = data
        controller.requestModelBuild()
    }

    inner class MovieAdapterController : BaseEpoxyController<Movies.MoviesResult?>() {

        override fun buildModels() {

            data?.let { movies ->

                val movieMap = movies.groupBy {
                    it?.movieCategory
                }
                movieMap.keys.forEachIndexed { index, movieCategory ->
                    carouselNoSnap {
                        MovieHeaderHolder { title ->
                            //requireView().showMessage(title)
                            movieCategory?.let {
                                launchFragment(NavigationDeepLinks.toAllMovies(it.value, it.formattedName))
                            }
                        }.apply {
                            title = movieCategory!!.formattedName
                            id(movieCategory.name.plus(index))
                            addTo(this@MovieAdapterController)
                        }

                        id(movieCategory?.name.plus(" carousel"))
                        models(movies.filter { movie ->
                            movie?.movieCategory == movieCategory
                        }.mapIndexed { index, moviesResult ->
                            if (moviesResult?.movieCategory == MovieCategory.IN_THEATER) {
                                MainMovieHolder {
                                    launchFragment(NavigationDeepLinks.toMovieDetails(it, moviesResult.title))
                                }.apply {
                                    moviesResult.let {
                                        title = it.title
                                        imageUrl = it.posterPath
                                        movieId = it.id
                                    }
                                    id(index)
                                }
                            } else {
                                SubMovieHolder {
                                    launchFragment(NavigationDeepLinks.toMovieDetails(it, moviesResult?.title ?: ""))
                                }.apply {
                                    moviesResult?.let {
                                        title = it.title
                                        imageUrl = it.posterPath
                                        movieId = it.id
                                    }
                                    id(index)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}
