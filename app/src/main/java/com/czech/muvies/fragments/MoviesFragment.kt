package com.czech.muvies.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.czech.muvies.adapters.*
import com.czech.muvies.databinding.MoviesFragmentBinding
import com.czech.muvies.models.*
import com.czech.muvies.models.Movies.MoviesResult.*
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.MovieRepository
import com.czech.muvies.utils.Status
import com.czech.muvies.utils.epoxy.BaseEpoxyController
import com.czech.muvies.utils.epoxy.carouselNoSnap
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.czech.muvies.viewModels.MovieViewModelFactory
import com.czech.muvies.viewModels.MoviesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: MoviesFragmentBinding
    private val controller: MovieAdapterController by lazy { MovieAdapterController() }

    private var TAG = "MoviesFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MoviesFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(
                MoviesApiService.getService(),
                MovieRepository((MoviesApiService.getService()))
            )
        )
            .get(MoviesViewModel::class.java)
        binding.lifecycleOwner = this
        binding.moviesViewModel = viewModel
        binding.movieEpoxyRecyclerView.adapter = controller.adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieResponse.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> binding.lottieProgress.makeVisible()
                Status.ERROR -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                    binding.lottieProgress.makeGone()
                }
                Status.SUCCESS -> {
                    binding.lottieProgress.makeGone()
                    controller.data = resource.data
                    controller.requestModelBuild()
                }
            }
        }
    }

    inner class MovieAdapterController : BaseEpoxyController<Movies.MoviesResult?>() {

        override fun buildModels() {
            val inTheater = mutableListOf<Movies.MoviesResult>()
            val trending = mutableListOf<Movies.MoviesResult>()
            val popular = mutableListOf<Movies.MoviesResult>()
            val topRated = mutableListOf<Movies.MoviesResult>()
            val upComing = mutableListOf<Movies.MoviesResult>()

            data?.let { movies ->
                movies.forEachIndexed { index, movie ->
                    when (movie?.movieCategory) {
                        MovieCategory.IN_THEATER -> {
                            movies[index]?.let { inTheater.add(it) }
                        }
                        MovieCategory.POPULAR -> {
                            movies[index]?.let { popular.add(it) }
                        }
                        MovieCategory.TRENDING -> {
                            movies[index]?.let { trending.add(it) }
                        }
                        MovieCategory.TOP_RATED -> {
                            movies[index]?.let { topRated.add(it) }
                        }
                        MovieCategory.UPCOMING -> {
                            movies[index]?.let { upComing.add(it) }
                        }
                    }
                }
            }

            carouselNoSnap {
                MovieHeaderHolder { title ->
                    Toast.makeText(requireContext(), title, Toast.LENGTH_SHORT).show()
                }.apply {
                    title = "In theater"
                    id("in_theater_header")
                    addTo(this@MovieAdapterController)
                }

                id("in_theater_carousel")
                models(inTheater.mapIndexed { index, moviesResult ->
                    MainMovieHolder().apply {
                        title = moviesResult.title
                        imageUrl = moviesResult.posterPath
                        id(index)
                    }
                })
            }

            carouselNoSnap {
                MovieHeaderHolder { title ->
                    Toast.makeText(requireContext(), title, Toast.LENGTH_SHORT).show()
                }.apply {
                    title = "Trending"
                    id("trending_header")
                    addTo(this@MovieAdapterController)
                }

                id("trending_carousel")
                models(trending.mapIndexed { index, moviesResult ->
                    SubMovieHolder().apply {
                        title = moviesResult.title
                        imageUrl = moviesResult.posterPath
                        id(index)
                    }
                })
            }

            carouselNoSnap {
                MovieHeaderHolder { title ->
                    Toast.makeText(requireContext(), title, Toast.LENGTH_SHORT).show()
                }.apply {
                    title = "Popular"
                    id("popular_header")
                    addTo(this@MovieAdapterController)
                }

                id("popular_carousel")
                models(popular.mapIndexed { index, moviesResult ->
                    SubMovieHolder().apply {
                        title = moviesResult.title
                        imageUrl = moviesResult.posterPath
                        id(index)
                    }
                })
            }

            carouselNoSnap {
                MovieHeaderHolder { title ->
                    Toast.makeText(requireContext(), title, Toast.LENGTH_SHORT).show()
                }.apply {
                    title = "Top rated"
                    id("top_rated_header")
                    addTo(this@MovieAdapterController)
                }

                id("top_rated_carousel")
                models(topRated.mapIndexed { index, moviesResult ->
                    SubMovieHolder().apply {
                        title = moviesResult.title
                        imageUrl = moviesResult.posterPath
                        id(index)
                    }
                })
            }

            carouselNoSnap {
                MovieHeaderHolder { title ->
                    Toast.makeText(requireContext(), title, Toast.LENGTH_SHORT).show()
                }.apply {
                    title = "Upcoming"
                    id("upcoming_header")
                    addTo(this@MovieAdapterController)
                }

                id("upcoming_carousel")
                models(trending.mapIndexed { index, moviesResult ->
                    SubMovieHolder().apply {
                        title = moviesResult.title
                        imageUrl = moviesResult.posterPath
                        id(index)
                    }
                })
            }
        }
    }
}
