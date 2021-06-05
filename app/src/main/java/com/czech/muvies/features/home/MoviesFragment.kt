package com.czech.muvies.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.czech.muvies.adapters.*
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
import com.czech.muvies.viewModels.MovieViewModelFactory
import com.czech.muvies.viewModels.MoviesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
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
                is Result.Loading -> binding.lottieProgress.makeVisible()
                is Result.Error -> {
                    requireView().showErrorMessage(result.error.localizedMessage)
                    binding.lottieProgress.makeGone()
                    Timber.e(result.error)
                }
                is Result.Success -> {
                    binding.lottieProgress.makeGone()
                    controller.data = result.data
                    controller.requestModelBuild()
                }
            }
        }
    }

    inner class MovieAdapterController : BaseEpoxyController<Movies.MoviesResult?>() {

        override fun buildModels() {

            data?.let { movies ->

                carouselNoSnap {
                    MovieHeaderHolder { title ->
                        requireView().showMessage(title)
                    }.apply {
                        title = "In theater"
                        id("in_theater_header")
                        addTo(this@MovieAdapterController)
                    }

                    id("in_theater_carousel")
                    models(movies.filter { movie ->
                        movie?.movieCategory == MovieCategory.IN_THEATER
                    }.mapIndexed { index, moviesResult ->
                        MainMovieHolder {
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
                    })
                }

                carouselNoSnap {
                    MovieHeaderHolder { title ->
                        requireView().showMessage(title)
                    }.apply {
                        title = "Trending"
                        id("trending_header")
                        addTo(this@MovieAdapterController)
                    }

                    id("trending_carousel")
                    models(movies.filter { movie ->
                        movie?.movieCategory == MovieCategory.TRENDING
                    }.mapIndexed { index, moviesResult ->
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
                    })
                }

                carouselNoSnap {
                    MovieHeaderHolder { title ->
                        requireView().showMessage(title)
                    }.apply {
                        title = "Popular"
                        id("popular_header")
                        addTo(this@MovieAdapterController)
                    }

                    id("popular_carousel")
                    models(movies.filter { movie ->
                        movie?.movieCategory == MovieCategory.POPULAR
                    }.mapIndexed { index, moviesResult ->
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
                    })
                }

                carouselNoSnap {
                    MovieHeaderHolder { title ->
                        requireView().showMessage(title)
                    }.apply {
                        title = "Top rated"
                        id("top_rated_header")
                        addTo(this@MovieAdapterController)
                    }

                    id("top_rated_carousel")
                    models(movies.filter { movie ->
                        movie?.movieCategory == MovieCategory.TOP_RATED
                    }.mapIndexed { index, moviesResult ->
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
                    })
                }

                carouselNoSnap {
                    MovieHeaderHolder { title ->
                        requireView().showMessage(title)
                    }.apply {
                        title = "Upcoming"
                        id("upcoming_header")
                        addTo(this@MovieAdapterController)
                    }

                    id("upcoming_carousel")
                    models(movies.filter { movie ->
                        movie?.movieCategory == MovieCategory.UPCOMING
                    }.mapIndexed { index, moviesResult ->
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
                    })
                }
            }
        }
    }
}
