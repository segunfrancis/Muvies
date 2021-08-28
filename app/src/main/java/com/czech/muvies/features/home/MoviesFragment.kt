package com.czech.muvies.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
import timber.log.Timber

class MoviesFragment : Fragment() {

    private lateinit var binding: MoviesFragmentBinding
    private val viewModel by viewModels<MoviesViewModel> { MovieViewModelFactory(MovieRepository((MoviesApiService.getService()))) }
    private val controller: MovieAdapterController by lazy { MovieAdapterController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MoviesFragmentBinding.inflate(inflater)
        binding.movieEpoxyRecyclerView.adapter = controller.adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                            requireView().showMessage(title)
                        }.apply {
                            title = movieCategory!!.value
                            id(movieCategory.name.plus(index))
                            addTo(this@MovieAdapterController)
                        }

                        id(movieCategory?.name.plus(" carousel"))
                        models(movies.filter { movie ->
                            movie?.movieCategory == movieCategory
                        }.mapIndexed { index, moviesResult ->
                            if (moviesResult?.movieCategory == MovieCategory.IN_THEATER) {
                                MainMovieHolder {
                                    launchFragment(
                                        MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                                            it
                                        )
                                    )
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
                                    launchFragment(
                                        MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                                            it
                                        )
                                    )
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
